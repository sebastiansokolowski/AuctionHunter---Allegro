package com.sebastian.sokolowski.auctionhunter.newTarget;

import com.sebastian.sokolowski.auctionhunter.database.models.Target;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class NewTargetPresenter implements NewTargetContract.Presenter {

    private final Target mTarget = new Target();
    private final NewTargetContract.View mView;

    public NewTargetPresenter(NewTargetContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void addCategory() {

    }
}
