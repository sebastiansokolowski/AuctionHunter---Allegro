package com.sebastian.sokolowski.auctionhunter.main;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.database.models.Cat;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataLimitEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataCountResponse.DoGetCatsDataCountResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.CatInfoType;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.DoGetCatsDataLimitResponse;

import io.realm.Realm;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private final RequestManager mRequestManager;
    private Realm mRealm = Realm.getDefaultInstance();

    public MainPresenter(MainContract.View view) {
        mView = view;
        mRequestManager = new RequestManager();
    }

    @Override
    public void start() {
        downloadCatsCount();
    }

    private void downloadCatsCount() {
        mRequestManager.doGetCatsDataCount(new SOAPObserver<DoGetCatsDataCountResponse, AllegroSOAPFault>() {
            @Override
            public void onCompletion(Request<DoGetCatsDataCountResponse, AllegroSOAPFault> request) {
                if (request.getResult() == null || request.getResult().getCatsCount() == null) {
                    return;
                }

                downloadCats(request.getResult().getCatsCount());
            }

            @Override
            public void onException(Request<DoGetCatsDataCountResponse, AllegroSOAPFault> request, SOAPException e) {
                if (request.getSOAPFault().getFaultString() != null) {
                    mView.showErrorProgressDialog(request.getSOAPFault().getFaultString());
                }
            }
        });
    }

    private void downloadCats(int count) {
        //remove all old data
        mRealm.beginTransaction();
        mRealm.delete(Cat.class);
        mRealm.commitTransaction();

        //set progress bar max
        int packageElement = 2500;
        int offset = (int) Math.ceil(count / (double) packageElement);
        mView.showProgressDialog(offset);
        int i = 0;

        for (; i != offset; i++) {
            DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope = new DoGetCatsDataLimitEnvelope();
            doGetCatsDataLimitEnvelope.setOffset(i);
            doGetCatsDataLimitEnvelope.setPackageElement(offset);

            mRequestManager.doGetCatsDataLimit(doGetCatsDataLimitEnvelope, new SOAPObserver<DoGetCatsDataLimitResponse, AllegroSOAPFault>() {
                @Override
                public void onCompletion(Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> request) {
                    if (request.getResult() == null || request.getResult().getCatInfoTypeList() == null) {
                        return;
                    }

                    mRealm.beginTransaction();

                    for (CatInfoType catInfoType :
                            request.getResult().getCatInfoTypeList()
                            ) {
                        Cat cat = new Cat();
                        cat.setCatId(catInfoType.getCatId());
                        cat.setCatName(catInfoType.getCatName());
                        cat.setCatParent(catInfoType.getCatParent());
                        cat.setCatPosition(catInfoType.getCatPosition());
                        cat.setCatIsProductCatalogueEnabled(catInfoType.getCatIsProductCatalogueEnabled());

                        mRealm.copyToRealm(cat);
                    }
                    mView.incrementProgressDialog();
                    mRealm.commitTransaction();
                }

                @Override
                public void onException(Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> request, SOAPException e) {
                    mRealm.cancelTransaction();
                    if (request.getSOAPFault().getFaultString() != null) {
                        mView.showErrorProgressDialog(request.getSOAPFault().getFaultString());
                    }
                }
            });
        }
    }

    @Override
    public void addNewTarget() {
        mView.showAddTarget();
    }

    @Override
    public void changeView(MainContract.View.Type type) {

    }

    @Override
    public void setActualTarget(Target actualTarget) {

    }

    @Override
    public void changeSortType(SortTypeEnum sortTypeEnum) {

    }

    @Override
    public void changeSortOrder(SortOrderEnum sortOrderEnum) {

    }

    @Override
    public void addToFavouriteTarget(Target target) {

    }

    @Override
    public void removeTarget(Target target) {

    }

    @Override
    public void deleteTarget() {

    }

    @Override
    public void editTarget() {

    }
}
