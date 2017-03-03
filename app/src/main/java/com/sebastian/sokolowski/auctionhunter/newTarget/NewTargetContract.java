package com.sebastian.sokolowski.auctionhunter.newTarget;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface NewTargetContract {
    interface View {
        void showAddTarget();

        void showViewType(Type type);

        void showLoadingProgress();

        void showNoTarget();

        void showTargets(List<Target> targets);

        void showErrorDialog();

        enum Type {LIST, GRID}

        enum SortType {PRICE, ADD_TIME, END_TIME}

        enum SortOrder {ASC, DESC}
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
