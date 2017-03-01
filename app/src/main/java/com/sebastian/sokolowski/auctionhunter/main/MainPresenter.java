package com.sebastian.sokolowski.auctionhunter.main;

import com.sebastian.sokolowski.auctionhunter.database.entity.Target;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;

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
    public void start() {

    }

    @Override
    public void deleteTarget() {

    }

    @Override
    public void editTarget() {

    }
}
