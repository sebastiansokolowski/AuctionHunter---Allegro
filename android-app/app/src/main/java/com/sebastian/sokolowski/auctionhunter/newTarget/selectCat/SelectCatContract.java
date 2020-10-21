package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat;

import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter.CatAdapterItem;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 22.02.17.
 */

public interface SelectCatContract {
    interface View {
        void showSelectCategoryFragment(String parentId);

        void setAdapterListItems(List<CatAdapterItem> adapterList);

        void showToastMessage(String message);

    }

    interface Presenter {
        void start();

        void categoryChildClicked(String categoryId);
    }
}
