package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat;

import android.content.Context;

import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter.CatAdapterItem;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroClient;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroService;
import com.sebastian.sokolowski.auctionhunter.rest.response.Categories;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sebastian Sokołowski on 05.03.17.
 */

public class SelectCatPresenter implements SelectCatContract.Presenter {
    private final Context mContext;
    private final String mParentId;
    private final SelectCatContract.View mView;
    private final AllegroService allegroService;

    public SelectCatPresenter(SelectCatFragment selectCatFragment, String parentId) {
        mContext = selectCatFragment.getContext();
        mView = selectCatFragment;
        mParentId = parentId;
        allegroService = new AllegroClient(mContext).getAllegroService();
    }

    @Override
    public void start() {
        getListItem();
    }

    @Override
    public void categoryChildClicked(String categoryId) {
        mView.showSelectCategoryFragment(categoryId);
    }

    private void getListItem() {
        allegroService.getCategories(mParentId).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                List<CatAdapterItem> adapterItemList = new ArrayList<>();
                for (CategoryDto categoryDto : response.body().getCategories()) {
                    CatAdapterItem item = new CatAdapterItem();
                    item.setId(categoryDto.getId());
                    item.setName(categoryDto.getName());
                    item.setHasChild(!categoryDto.getLeaf());

                    adapterItemList.add(item);
                }

                mView.setAdapterListItems(adapterItemList);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                mView.showToastMessage(t.getMessage());
            }
        });
    }

}
