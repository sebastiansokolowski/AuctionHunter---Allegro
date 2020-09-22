package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.content.Context;
import android.view.View;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterModel;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroClient;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroService;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameter;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class NewTargetPresenter implements NewTargetContract.Presenter {
    private final NewTargetContract.View mView;
    private final Realm mRealm = Realm.getDefaultInstance();
    private final Context mContext;
    private HashMap<FilterModel, View> mDefaultFiltersHashMap;
    private HashMap<FilterModel, View> mFiltersHashMap;
    private AllegroService allegroService;

    //data
    private String mCatId;

    public NewTargetPresenter(NewTargetFragment newTargetFragment) {
        mView = newTargetFragment;
        mContext = newTargetFragment.getContext();
        allegroService = new AllegroClient(mContext).getAllegroService();
    }

    @Override
    public void start() {
        mDefaultFiltersHashMap = FilterHelper.createDefaultFiltersViews(mContext);

        for (Map.Entry<FilterModel, View> entry : mDefaultFiltersHashMap.entrySet()
        ) {
            mView.addFilterView(entry.getValue());
        }
    }

    @Override
    public void onCategoryClickListener(String catId) {
        mCatId = catId;

        //loading categories
        allegroService.getCategoryParameters(catId).enqueue(new Callback<CategoryParameters>() {
            @Override
            public void onResponse(Call<CategoryParameters> call, Response<CategoryParameters> response) {
                mView.setLoadingFilters(false);

                if (response.body() == null) {
                    mView.showErrorMessage(mContext.getString(R.string.new_target_no_filters_info));
                    return;
                }
                List<CategoryParameter> categoryParameters = response.body().getParameters();
                if (categoryParameters.size() == 0) {
                    mView.showErrorMessage(mContext.getString(R.string.new_target_no_filters_info));
                    return;
                }
                mFiltersHashMap = FilterHelper.createFiltersViews(mContext, categoryParameters, true);

                for (Map.Entry<FilterModel, View> entry : mFiltersHashMap.entrySet()
                ) {
                    mView.addFilterView(entry.getValue());
                }
            }

            @Override
            public void onFailure(Call<CategoryParameters> call, Throwable t) {
                mView.setLoadingFilters(false);
                mView.showErrorMessage(t.getMessage());
            }
        });
    }

    @Override
    public void save(Target target) {
        if (mCatId == null || mFiltersHashMap == null) {
            mView.showToastMessage(mContext.getString(R.string.new_target_message_select_category));
            return;
        }
        if (target.getDrawerName().equals("")) {
            mView.showToastMessage(mContext.getString(R.string.new_target_message_enter_target_name));
            return;
        }
        if (target.getSearchingName().equals("")) {
            mView.showToastMessage(mContext.getString(R.string.new_target_message_enter_searching_name));
            return;
        }

        for (Map.Entry<FilterModel, View> entry : mFiltersHashMap.entrySet()) {
            target.addFilterModel(entry.getKey());
        }
        for (Map.Entry<FilterModel, View> entry : mDefaultFiltersHashMap.entrySet()) {
            target.addFilterModel(entry.getKey());
        }

        target.setCategoryId(mCatId);

        mRealm.beginTransaction();
        mRealm.copyToRealm(target);
        mRealm.commitTransaction();

        mView.finishActivity();
    }
}
