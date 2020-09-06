package com.sebastian.sokolowski.auctionhunter.main;

import android.content.Context;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {
    private String TAG = MainPresenter.class.getSimpleName();

    private final MainContract.View mView;
    private final RequestManager mRequestManager = new RequestManager();
    private Realm mRealm = Realm.getDefaultInstance();
    private Context mContext;

    private Target mCurrentTarget;

    public MainPresenter(MainActivity mainActivity) {
        mView = mainActivity;
        mContext = mainActivity;
    }

    @Override
    public void start() {
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
        mView.showSelectTargetButton();

        mRealm.beginTransaction();
        mCurrentTarget.deleteFromRealm();
        mRealm.commitTransaction();

        refreshDrawerAdapter();
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

                List<TargetItem> targetItems = FilterHelper.createTargetItems(request.getResult().getItemList());

                if (targetItems.size() > 0) {
                    mCurrentTarget.addTargetItemsToAllItems(targetItems);

                    mView.setMainAdapterList(mCurrentTarget.getAllItems());
                } else {
                    mView.showNoDataInfo();
                }

                mRealm.commitTransaction();
            }

            @Override
            public void onException(Request<DoGetItemsListResponse, AllegroSOAPFault> request, SOAPException e) {
                if (request.getSOAPFault() == null || request.getSOAPFault().getFaultString() == null) {
                    mView.showErrorToast(mContext.getString(R.string.main_activity_error));
                    return;
                }
                if (request.getSOAPFault().getFaultString() != null) {
                    mView.showErrorToast(request.getSOAPFault().getFaultString());
                }
            }
        });
    }

    @Override
    public void clickTargetItem(TargetItem targetItem) {
        mView.showTargetItem(mContext.getString(R.string.ALLEGRO_URL_ITEM) + targetItem.getId());
    }

    @Override
    public void refreshTargetItems() {
        refreshTargetList();
    }
}
