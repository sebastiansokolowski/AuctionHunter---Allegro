package com.sebastian.sokolowski.auctionhunter.newTarget;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface NewTargetContract {
    interface View {
        void showSelectCategoryFragment();

        void setLoadingFilters(boolean loading);

        void setFilters(View filters);

        void showErrorToast(String message);

        void finishActivity();
    }

    interface Presenter {
        void start();

        void addCategoryClicked(int catId);

        void save(Target target);
    }

    interface OnClickCatItemListener {
        void onClickedCatItem(int catId);
    }
}
