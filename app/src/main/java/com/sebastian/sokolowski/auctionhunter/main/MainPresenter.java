package com.sebastian.sokolowski.auctionhunter.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Cat;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataLimitEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataCountResponse.DoGetCatsDataCountResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.CatInfoType;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.DoGetCatsDataLimitResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.Item;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.PhotoInfoType;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.PriceInfoType;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {
    private final String ALLEGRO_URL_ITEM = "http://allegro.pl/show_item.php?item=";

    private final MainContract.View mView;
    private final RequestManager mRequestManager = new RequestManager();
    private Realm mRealm = Realm.getDefaultInstance();
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    //cats download
    private int completionCatsData = 0;
    private Target mCurrentTarget;

    public MainPresenter(MainActivity mainActivity) {
        mView = mainActivity;
        mContext = mainActivity;
        mSharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public void start() {
        if (!mSharedPreferences.getBoolean(mContext.getString(R.string.SHARED_PREFERENCES_DOWNLOADED_CATS), false)) {
            downloadCatsCount();
        }

        refreshDrawerAdapter();
        checkIfTargetIsSelected();
    }

    private void checkIfTargetIsSelected() {
        if (mCurrentTarget == null) {
            mView.showSelectTargetButton();
        }
    }

    private void refreshDrawerAdapter() {
        List<Target> targetList = mRealm.where(Target.class).findAllSorted("drawerName");
        if (targetList.size() == 0) {
            mView.showNoTargetInfo();
        } else {
            mView.setDrawerAdapterList(targetList);
        }
    }

    private void finishedDownloadCats() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mContext.getString(R.string.SHARED_PREFERENCES_DOWNLOADED_CATS), true);
        editor.commit();
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
        final int maxOffset = (int) Math.ceil(count / (double) packageElement);
        mView.showProgressDialog(maxOffset);
        int currentOffset = 0;
        completionCatsData = 0;

        for (; currentOffset != maxOffset; currentOffset++) {
            DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope = new DoGetCatsDataLimitEnvelope();
            doGetCatsDataLimitEnvelope.setOffset(currentOffset);
            doGetCatsDataLimitEnvelope.setPackageElement(packageElement);

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

                    completionCatsData++;
                    if (completionCatsData == maxOffset) {
                        finishedDownloadCats();
                    }
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

    @Override
    public void changeTarget(final Target target) {
        mCurrentTarget = target;
        refreshTargetList();
        mView.showLoadingProgress(true);
    }

    private void refreshTargetList() {
        if (mCurrentTarget == null) {
            return;
        }
        mRequestManager.doGetItemsList(mCurrentTarget, new SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault>() {
            @Override
            public void onCompletion(Request<DoGetItemsListResponse, AllegroSOAPFault> request) {
                mView.showLoadingProgress(false);
                if (request.getResult() == null || request.getResult().getItemList() == null) {
                    mView.showNoDataInfo();
                    return;
                }

                mRealm.beginTransaction();
                mCurrentTarget.getAllItems().clear();

                for (Item item : request.getResult().getItemList()
                        ) {
                    TargetItem targetItem = new TargetItem();
                    targetItem.setId(item.getItemId());
                    targetItem.setName(item.getItemTitle());
                    for (PhotoInfoType photoInfoType : item.getPhotosInfo()
                            ) {
                        switch (photoInfoType.getPhotoSize()) {
                            case PHOTO_TYPE_LARGE:
                                targetItem.setImageUrl(photoInfoType.getPhotoUrl());
                                break;
                        }
                    }

                    for (PriceInfoType priceInfoType : item.getPriceInfo()
                            ) {
                        switch (priceInfoType.getPriceType()) {
                            case PRICE_TYPE_BIDDING:
                                if (targetItem.getOffertype() == null) {
                                    targetItem.setOffertype(TargetItem.Offertype.AUCTION);
                                } else {
                                    targetItem.setOffertype(TargetItem.Offertype.BOTH);
                                }
                                targetItem.setPriceBid(priceInfoType.getPriceValue());
                                break;
                            case PRICE_TYPE_BUY_NOW:
                                if (targetItem.getOffertype() == null) {
                                    targetItem.setOffertype(TargetItem.Offertype.BUY_NOW);
                                } else {
                                    targetItem.setOffertype(TargetItem.Offertype.BOTH);
                                }
                                targetItem.setPrice(priceInfoType.getPriceValue());
                                break;
                            case PRICE_TYPE_WITH_DELIVERY:
                                targetItem.setPriceFull(priceInfoType.getPriceValue());
                                break;
                        }
                    }

                    mCurrentTarget.addTargetItemToAllItems(targetItem);
                }

                mRealm.commitTransaction();

                if (mCurrentTarget.getAllItems().size() == 0) {
                    mView.showNoDataInfo();
                } else {
                    mView.setMainAdapterList(mCurrentTarget.getAllItems());
                }
            }

            @Override
            public void onException(Request<DoGetItemsListResponse, AllegroSOAPFault> request, SOAPException e) {
                if (request.getSOAPFault().getFaultString() != null) {
                    mView.showErrorToast(request.getSOAPFault().getFaultString());
                }
            }
        });
    }

    @Override
    public void clickTargetItem(TargetItem targetItem) {
        mView.showTargetItem(ALLEGRO_URL_ITEM + targetItem.getId());
    }

    @Override
    public void refreshTargetItems() {
        refreshTargetList();
    }
}
