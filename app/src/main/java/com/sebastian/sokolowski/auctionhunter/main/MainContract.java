package com.sebastian.sokolowski.auctionhunter.main;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface MainContract {
    interface View {
        void showAddTarget();

        void showViewType(Type type);

        void showLoadingProgressBar();

        void showNoTarget();

        void showTargets(List<Target> targets);

        void showErrorDialog();

        void showProgressDialog(int max);

        void showErrorProgressDialog(String message);

        void incrementProgressDialog();

        enum Type {LIST, GRID}
    }

    interface Presenter {
        void addNewTarget();

        void changeView(View.Type type);

        void setActualTarget(Target actualTarget);

        void changeSortType(SortTypeEnum sortTypeEnum);

        void changeSortOrder(SortOrderEnum sortOrderEnum);

        void addToFavouriteTarget(Target target);

        void removeTarget(Target target);

        void start();

        void deleteTarget();

        void editTarget();
    }
}
