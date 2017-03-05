package com.sebastian.sokolowski.auctionhunter.newTarget;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface NewTargetContract {
    interface View {
        void showSelectCategoryFragment();
    }

    interface Presenter {
        void start();

        void addCategory();
    }

    interface OnClickCatItemListener {
        void onClickedCatItem(int catId);
    }
}
