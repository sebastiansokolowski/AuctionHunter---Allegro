package com.sebastian.sokolowski.auctionhunter.newTarget;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class NewTargetPresenter implements NewTargetContract.Presenter {

    private final NewTargetContract.View mView;

    public NewTargetPresenter(NewTargetContract.View view) {
        mView = view;
    }

    @Override
    public void addNewTarget() {

    }

    @Override
    public void changeView(NewTargetContract.View.Type type) {

    }

    @Override
    public void setActualTarget(Target actualTarget) {

    }

    @Override
    public void changeSortType(NewTargetContract.View.SortType sortType) {

    }

    @Override
    public void changeSortOrder(NewTargetContract.View.SortOrder sortOrder) {

    }

    @Override
    public void addToFavouriteTarget(Target target) {

    }

    @Override
    public void removeTarget(Target target) {

    }
}
