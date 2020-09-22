package com.sebastian.sokolowski.auctionhunter.main;

import android.content.Context;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroClient;
import com.sebastian.sokolowski.auctionhunter.rest.request.SortType;
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {
    private String TAG = MainPresenter.class.getSimpleName();

    private final MainContract.View mView;
    private Realm mRealm = Realm.getDefaultInstance();
    private Context mContext;
    private AllegroClient allegroClient;

    private Target mCurrentTarget;

    public MainPresenter(MainActivity mainActivity) {
        mView = mainActivity;
        mContext = mainActivity;
        allegroClient = new AllegroClient(mContext);
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
    public void changeSortType(SortType sortType) {

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
        allegroClient.getOffers(mCurrentTarget).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                mView.showLoadingProgress(false);

                mRealm.beginTransaction();
                mCurrentTarget.getAllItems().clear();

                List<TargetItem> targetItems = FilterHelper.createTargetItems(response.body());

                if (targetItems.size() > 0) {
                    mCurrentTarget.addTargetItemsToAllItems(targetItems);

                    mView.setMainAdapterList(mCurrentTarget.getAllItems());
                } else {
                    mView.showNoDataInfo();
                }

                mRealm.commitTransaction();
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {
                mView.showErrorToast(t.getMessage());
            }
        });
    }

    @Override
    public void clickTargetItem(TargetItem targetItem) {
        if (targetItem.getUrl() != null) {
            mView.showTargetItem(targetItem.getUrl());
        } else {
            mView.showTargetItem(mContext.getString(R.string.ALLEGRO_URL_ITEM) + targetItem.getId());
        }
    }

    @Override
    public void refreshTargetItems() {
        refreshTargetList();
    }
}
