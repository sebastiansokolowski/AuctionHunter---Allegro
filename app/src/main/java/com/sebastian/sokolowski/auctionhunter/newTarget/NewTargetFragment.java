package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.SelectCatFragment;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class NewTargetFragment extends Fragment implements NewTargetContract.View {

    private NewTargetPresenter mNewTargetPresenter;

    private Button mAddCategory;

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

        mAddCategory = (Button) view.findViewById(R.id.btn_add_category);
        mAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCategoryFragment();
            }
        });

        return view;
    }

    @Override
    public void showSelectCategoryFragment() {
        SelectCatFragment selectCatFragment = SelectCatFragment.newInstance();
        selectCatFragment.setParentId(0);
        selectCatFragment.setOnClickCatItemListener(new NewTargetContract.OnClickCatItemListener(){
            @Override
            public void onClickedCatItem(int actId) {
                mNewTargetPresenter.addCategory();
            }
        });

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selectCatFragment)
                .addToBackStack("")
                .commit();
    }
}
