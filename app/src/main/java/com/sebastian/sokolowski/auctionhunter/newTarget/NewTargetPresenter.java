package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.content.Context;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;

import io.realm.Realm;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class NewTargetPresenter implements NewTargetContract.Presenter {
    private final NewTargetContract.View mView;
    private final Realm mRealm;
    private final Context mContext;

    //data
    private Integer mCatId;

    public NewTargetPresenter(NewTargetFragment newTargetFragment) {
        mView = newTargetFragment;
        mContext = newTargetFragment.getContext();

        Realm.init(mContext);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void addCategoryClicked(int catId) {
        mCatId = catId;
    }

    @Override
    public void save(Target target) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(target);
        mRealm.commitTransaction();

        mView.finishActivity();
    }
}
