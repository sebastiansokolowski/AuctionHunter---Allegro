package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private String mParentId;
    private NewTargetContract.OnClickCatItemListener mOnClickCatItemListener;

    public SelectCatFragment() {
    }

    public static SelectCatFragment newInstance() {
        return new SelectCatFragment();
    }

    public void setParentId(String parentId) {
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
    public void showSelectCategoryFragment(String parentId) {
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
    public void onCategoryChildrenClicked(String categoryId) {
        mSelectCatPresenter.categoryChildClicked(categoryId);
    }

    @Override
    public void onCategoryClicked(String categoryId) {
        getFragmentManager().popBackStack(NewTargetFragment.class.getSimpleName(), 0);
        if (mOnClickCatItemListener != null) {
            mOnClickCatItemListener.onClickedCatItem(categoryId);
        }
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setOnClickCatItemListener(NewTargetContract.OnClickCatItemListener onClickCatItemListener) {
        mOnClickCatItemListener = onClickCatItemListener;
    }
}
