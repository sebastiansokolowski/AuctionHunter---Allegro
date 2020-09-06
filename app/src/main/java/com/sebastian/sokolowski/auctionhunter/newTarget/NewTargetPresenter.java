package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.content.Context;
import android.view.View;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterModel;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetItemsListEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.FilterOptionsType;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.FilterItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class NewTargetPresenter implements NewTargetContract.Presenter {
    private final NewTargetContract.View mView;
    private final Realm mRealm = Realm.getDefaultInstance();
    private final Context mContext;
    private final RequestManager mRequestManager = new RequestManager();
    private HashMap<FilterModel, View> mFiltersHashMap;

    //data
    private String mCatId;

    public NewTargetPresenter(NewTargetFragment newTargetFragment) {
        mView = newTargetFragment;
        mContext = newTargetFragment.getContext();
    }

    @Override
    public void start() {

    }

    @Override
    public void onCategoryClickListener(String catId) {
        mCatId = catId;

        //loading categories
        FilterOptionsType categoryFilter = new FilterOptionsType();
        categoryFilter.setFilterId("category");
        categoryFilter.addFilterValueId(catId + "");

        DoGetItemsListEnvelope doGetItemsListEnvelope = new DoGetItemsListEnvelope();
        doGetItemsListEnvelope.setResultScope(4);
        doGetItemsListEnvelope.addFilterOptionsType(categoryFilter);

        mRequestManager.deGetItemsList(doGetItemsListEnvelope, new SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault>() {
            @Override
            public void onCompletion(Request<DoGetItemsListResponse, AllegroSOAPFault> request) {
                mView.setLoadingFilters(false);

                if (request.getResult() == null || request.getResult().getFilterItemList() == null) {
                    mView.showErrorMessage(mContext.getString(R.string.new_target_no_filters_info));
                    return;
                }
                List<FilterItem> filterItemList = request.getResult().getFilterItemList();
                if (filterItemList.size() == 0) {
                    mView.showErrorMessage(mContext.getString(R.string.new_target_no_filters_info));
                    return;
                }
                mFiltersHashMap = FilterHelper.createFiltersViews(mContext, filterItemList);

                for (Map.Entry<FilterModel, View> entry : mFiltersHashMap.entrySet()
                        ) {
                    mView.addFilterView(entry.getValue());
                }
            }

            @Override
            public void onException(Request<DoGetItemsListResponse, AllegroSOAPFault> request, SOAPException e) {
                if(request.getSOAPFault() == null || request.getSOAPFault().getFaultString() == null){
                    mView.showErrorMessage(mContext.getString(R.string.main_activity_error));
                    return;
                }
                mView.setLoadingFilters(false);
                mView.showErrorMessage(request.getSOAPFault().getFaultString());
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

        target.setCategoryId(mCatId);

        mRealm.beginTransaction();
        mRealm.copyToRealm(target);
        mRealm.commitTransaction();

        mView.finishActivity();
    }
}
