package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.newTarget.NewTargetContract;
import com.sebastian.sokolowski.auctionhunter.newTarget.NewTargetFragment;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter.CatAdapterItem;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter.SelectCatAdapter;

import java.util.List;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class SelectCatFragment extends ListFragment implements SelectCatContract.View, SelectCatAdapter.OnClickListenerCatItem {

    private SelectCatAdapter mSelectCatAdapter;
    private SelectCatPresenter mSelectCatPresenter;
    private Integer mParentId = 0;
    private NewTargetContract.OnClickCatItemListener mOnClickCatItemListener;

    public SelectCatFragment() {
    }

    public static SelectCatFragment newInstance() {
        return new SelectCatFragment();
    }

    public void setParentId(Integer parentId) {
        mParentId = parentId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectCatAdapter = new SelectCatAdapter(getContext());
        mSelectCatAdapter.setCategoryListener(this);
        mSelectCatPresenter = new SelectCatPresenter(this, mParentId);

        setListAdapter(mSelectCatAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSelectCatPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_cat, container, false);
    }

    @Override
    public void showSelectCategoryFragment(int parentId) {
        SelectCatFragment selectCatFragment = SelectCatFragment.newInstance();
        selectCatFragment.setParentId(parentId);
        selectCatFragment.setOnClickCatItemListener(mOnClickCatItemListener);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selectCatFragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void setAdapterListItems(List<CatAdapterItem> adapterList) {
        mSelectCatAdapter.clear();
        mSelectCatAdapter.addItems(adapterList);
    }

    @Override
    public void onCategoryChildrenClicked(int categoryId) {
        mSelectCatPresenter.categoryChildClicked(categoryId);
    }

    @Override
    public void onCategoryClicked(int categoryId) {
        getFragmentManager().popBackStack(NewTargetFragment.class.getSimpleName(), 0);
        if (mOnClickCatItemListener != null) {
            mOnClickCatItemListener.onClickedCatItem(categoryId);
        }
    }

    public void setOnClickCatItemListener(NewTargetContract.OnClickCatItemListener onClickCatItemListener) {
        mOnClickCatItemListener = onClickCatItemListener;
    }
}
