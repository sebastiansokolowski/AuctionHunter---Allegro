package com.sebastian.sokolowski.auctionhunter.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.main.MainActivity;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;

import io.realm.Realm;

/**
 * Created by Sebastian Sokołowski on 02.03.17.
 */

public class SearchService extends Service {
    private static int NOTIFICATION_ID = 1112;
    private final RequestManager mRequestManager = new RequestManager();
    private Realm mRealm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
