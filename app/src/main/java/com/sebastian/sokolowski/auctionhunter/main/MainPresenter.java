package com.sebastian.sokolowski.auctionhunter.main;

import com.sebastian.sokolowski.auctionhunter.database.entity.Target;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
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
    public void changeSortType(MainContract.View.SortType sortType) {

    }

    @Override
    public void changeSortOrder(MainContract.View.SortOrder sortOrder) {

    }

    @Override
    public void addToFavouriteTarget(Target target) {

    }

    @Override
    public void removeTarget(Target target) {

    }
}
