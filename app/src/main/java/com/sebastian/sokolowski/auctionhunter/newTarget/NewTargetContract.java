package com.sebastian.sokolowski.auctionhunter.newTarget;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface NewTargetContract {
    interface View {
        void showSelectCategoryFragment();

        void setLoadingFilters(boolean loading);

        void addFilterView(android.view.View view);

        void showErrorMessage(String message);

        void finishActivity();
    }

    interface Presenter {
        void start();

        void onCategoryClickListener(int catId);

        void save(Target target);
    }

    interface OnClickCatItemListener {
        void onClickedCatItem(int catId);
    }
}
