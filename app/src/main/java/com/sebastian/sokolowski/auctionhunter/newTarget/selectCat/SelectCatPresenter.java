package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat;

import android.content.Context;

import com.sebastian.sokolowski.auctionhunter.database.models.Cat;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter.CatAdapterItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Sebastian Sokołowski on 05.03.17.
 */

public class SelectCatPresenter implements SelectCatContract.Presenter {
    private final Context mContext;
    private final Integer mParentId;
    private final SelectCatContract.View mView;
    private final Realm mRealm;

    public SelectCatPresenter(SelectCatFragment selectCatFragment, Integer parentId) {
        mContext = selectCatFragment.getActivity();
        mView = selectCatFragment;
        mParentId = parentId;

        Realm.init(mContext);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void start() {
        getListItem();
    }

    @Override
    public void categoryChildClicked(int categoryId) {
        mView.showSelectCategoryFragment(categoryId);
    }

    private void getListItem() {
        List<CatAdapterItem> adapterItemList = new ArrayList<>();

        List<Cat> cats = mRealm.where(Cat.class).equalTo("catParent", mParentId).findAll();
        for (Cat cat : cats
                ) {
            CatAdapterItem item = new CatAdapterItem();
            item.setId(cat.getCatId());
            item.setName(cat.getCatName());

            Cat child = mRealm.where(Cat.class).equalTo("catParent", item.getId()).findFirst();
            if (child == null) {
                item.setHasChild(false);
            } else {
                item.setHasChild(true);
            }

            adapterItemList.add(item);
        }

        mView.setAdapterListItems(adapterItemList);
    }
}
