package com.sebastian.sokolowski.auctionhunter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Sebastian Sokołowski on 02.03.17.
 */

public class SearchService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
