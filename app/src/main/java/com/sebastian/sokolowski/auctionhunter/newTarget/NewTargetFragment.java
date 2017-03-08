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
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.SelectCatFragment;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class NewTargetFragment extends Fragment implements NewTargetContract.View {

    private NewTargetPresenter mNewTargetPresenter;
    private LinearLayout mCategoryContainer;
    private TextView mSelectCategoryInfo;

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
        mSelectCategoryInfo = (TextView) view.findViewById(R.id.tv_select_category_info);
        Button mSelectCategoryButton = (Button) view.findViewById(R.id.btn_add_category);
        Button mSaveButton = (Button) view.findViewById(R.id.btn_save);


        mSelectCategoryButton.setOnClickListener(new View.OnClickListener() {
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

                //TODO: add other filters  to item
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
                mNewTargetPresenter.onCategoryClickListener(catId);
                mSelectCategoryInfo.setVisibility(View.GONE);
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
        mSelectCategoryInfo.setVisibility(View.GONE);
    }

    @Override
    public void addFilterView(View view) {
        mCategoryContainer.addView(view);
    }

    @Override
    public void showErrorMessage(String message) {
        mSelectCategoryInfo.setText(message);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}
