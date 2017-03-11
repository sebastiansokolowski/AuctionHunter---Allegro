package com.sebastian.sokolowski.auctionhunter;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Sebastian Sokołowski on 11.03.17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
