package com.sebastian.sokolowski.auctionhunter.main;

import com.sebastian.sokolowski.auctionhunter.database.entity.Target;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface MainContract {
    interface View {
        enum Type {LIST, GRID}

        enum SortType {PRICE, ADD_TIME, END_TIME}

        enum SortOrder {ASC, DESC}

        void showAddTarget();

        void showViewType(Type type);

        void showLoadingProgress();

        void showNoTarget();

        void showTargets(List<Target> targets);

        void showErrorDialog();
    }

    interface Presenter {
        void addNewTarget();

        void changeView(View.Type type);

        void setActualTarget(Target actualTarget);

        void changeSortType(View.SortType sortType);

        void changeSortOrder(View.SortOrder sortOrder);

        void addToFavouriteTarget(Target target);

        void removeTarget(Target target);
    }
}
