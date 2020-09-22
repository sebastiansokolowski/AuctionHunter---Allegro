package com.sebastian.sokolowski.auctionhunter.main;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.rest.request.SortType;
import com.sebastian.sokolowski.auctionhunter.utils.DialogHelper;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface MainContract {
    interface View {
        void showAddTarget();

        void showViewType(Type type);

        void showNoDataInfo();

        void showNoTargetInfo();

        void showSelectTargetButton();

        void showErrorToast(String message);

        void showLoadingProgress(boolean loading);

        void showTargets(List<Target> targets);

        void showErrorDialog();

        void showTargetItem(String url);

        void showProgressDialog(int max);

        void showErrorProgressDialog(String message);

        void incrementProgressDialog();

        void setDrawerAdapterList(List<Target> list);

        void setMainAdapterList(List<TargetItem> list);

        enum Type {LIST, GRID}
    }

    interface Presenter {
        void addNewTarget();

        void changeView(View.Type type);

        void setActualTarget(Target actualTarget);

        void changeSortType(SortType sortType);

        void addToFavouriteTarget(Target target);

        void removeTarget(Target target);

        void start();

        void deleteTarget();

        void editTarget();

        void changeTarget(Target target);

        void clickTargetItem(TargetItem targetItem);

        void refreshTargetItems();
    }
}
