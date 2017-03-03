package com.sebastian.sokolowski.auctionhunter.main;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.database.entity.Target;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataLimitEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.DoGetCatsDataLimitResponse;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private final RequestManager requestManager;

    public MainPresenter(MainContract.View view) {
        mView = view;
        requestManager = new RequestManager();
    }

    @Override
    public void start() {
        downloadCats();

    }

    private void downloadCats() {
        int i = 0;
        int max = 10;
        mView.showProgressDialog(max);
        for (; i != max; i++) {
            DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope = new DoGetCatsDataLimitEnvelope();
            doGetCatsDataLimitEnvelope.setOffset(max);
            doGetCatsDataLimitEnvelope.setPackageElement(i);

            requestManager.doGetCatsDataLimit(doGetCatsDataLimitEnvelope, new SOAPObserver<DoGetCatsDataLimitResponse, AllegroSOAPFault>() {
                @Override
                public void onCompletion(Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> request) {
                    mView.incrementProgressDialog();
                }

                @Override
                public void onException(Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> request, SOAPException e) {
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
