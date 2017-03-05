package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.SelectCatFragment;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class NewTargetFragment extends Fragment implements NewTargetContract.View {

    private NewTargetPresenter mNewTargetPresenter;
    private LinearLayout mCategoryContainer;

    public NewTargetFragment() {
    }

    public static NewTargetFragment newInstance() {
        return new NewTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNewTargetPresenter = new NewTargetPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNewTargetPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_target, container, false);

        mCategoryContainer = (LinearLayout) view.findViewById(R.id.category_container);
        final EditText mTargetName = (EditText) view.findViewById(R.id.et_target_name);
        final EditText mSearchingName = (EditText) view.findViewById(R.id.et_searching_name);
        Button mAddCategoryButton = (Button) view.findViewById(R.id.btn_add_category);
        final EditText mMaxPrice = (EditText) view.findViewById(R.id.et_max_price);
        final EditText mMinPrice = (EditText) view.findViewById(R.id.et_min_price);
        final Spinner mOfferType = (Spinner) view.findViewById(R.id.sp_offer_type);
        final Spinner mCondition = (Spinner) view.findViewById(R.id.sp_condition);
        Button mSaveButton = (Button) view.findViewById(R.id.btn_save);


        mAddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCategoryFragment();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Target target = new Target();
                target.setDrawerName(mTargetName.getText().toString());
                target.setSearchingName(mSearchingName.getText().toString());
                target.setPriceMax(mMaxPrice.getText().toString());
                target.setPriceMin(mMinPrice.getText().toString());
                target.setOfferType(mOfferType.getSelectedItem().toString());
                target.setCondition(mCondition.getSelectedItem().toString());

                mNewTargetPresenter.save(target);
            }
        });

        return view;
    }

    @Override
    public void showSelectCategoryFragment() {
        SelectCatFragment selectCatFragment = SelectCatFragment.newInstance();
        selectCatFragment.setParentId(0);
        selectCatFragment.setOnClickCatItemListener(new NewTargetContract.OnClickCatItemListener() {
            @Override
            public void onClickedCatItem(int catId) {
                mNewTargetPresenter.addCategoryClicked(catId);
            }
        });

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selectCatFragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void setLoadingFilters(boolean loading) {

    }

    @Override
    public void setFilters(NewTargetContract.View filters) {

    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}
