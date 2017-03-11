package com.sebastian.sokolowski.auctionhunter.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

/**
 * Created by Sebastian Sokołowski on 02.03.17.
 */

public class SearchService extends Service {
    private static String TAG = SearchService.class.getSimpleName();
    private static int NOTIFICATION_ID = 2;

    private final RequestManager mRequestManager = new RequestManager();
    private Realm mRealm = Realm.getDefaultInstance();
    private SharedPreferences mSharedPreferences;

    private long searchingInterval;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO: change share pref name
        searchingInterval = mSharedPreferences.getLong("searching_interval", TimeUnit.MINUTES.toMillis(1));

        //start searching targets
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                searchingNewTargets();

                mHandler.postDelayed(this, searchingInterval);
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void searchingNewTargets() {
        Log.d(TAG, "Start searching new targets, data:" + new Date().toString());
        List<Target> targetList = mRealm.where(Target.class).findAll();
        for (final Target target : targetList
                ) {
            mRequestManager.doGetItemsList(target, new SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault>() {
                @Override
                public void onCompletion(Request<DoGetItemsListResponse, AllegroSOAPFault> request) {
                    if (request.getResult() == null || request.getResult().getItemList() == null) {
                        Log.d(TAG, "No results");
                        return;
                    }

                    List<TargetItem> targetItems = FilterHelper.createTargetItems(request.getResult().getItemList());
                    for (TargetItem downloadedTargetItem : targetItems
                            ) {
                        boolean exist = false;
                        for (TargetItem savedTargetItem : target.getAllItems()
                                ) {
                            if (savedTargetItem.equals(downloadedTargetItem)) {
                                exist = true;
                                break;
                            }
                        }

                        if (!exist) {
                            mRealm.beginTransaction();
                            target.getAllItems().add(downloadedTargetItem);
                            target.getNewItems().add(downloadedTargetItem);
                            mRealm.commitTransaction();

                            showNotification(downloadedTargetItem);
                        }
                    }
                }

                @Override
                public void onException(Request<DoGetItemsListResponse, AllegroSOAPFault> request, SOAPException e) {
                    if (request.getSOAPFault().getFaultString() != null) {
                        Log.d(TAG, "Target: " + target.toString());
                        Log.d(TAG, "onException: " + request.getSOAPFault().getFaultString());
                    }
                }
            });
        }
    }

    private void showNotification(TargetItem targetItem) {
        NOTIFICATION_ID++;

        String title = "";
        switch (targetItem.getOffertype()) {
            case AUCTION:
                title = targetItem.getPriceBid() + " " + getString(R.string.currency_name);
                break;
            case BOTH:
                if (targetItem.getPriceBid() > targetItem.getPrice()) {
                    title = targetItem.getPrice() + " " + getString(R.string.currency_name);
                } else {
                    title = targetItem.getPriceBid() + " " + getString(R.string.currency_name);
                }
                break;
            case BUY_NOW:
                title = targetItem.getPrice() + " " + getString(R.string.currency_name);
                break;
        }

        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String url = getString(R.string.ALLEGRO_URL_ITEM) + targetItem.getId();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(targetItem.getName())
                .setSmallIcon(R.mipmap.ic_launcher);

        if (targetItem.getImageUrl() != null && !targetItem.getImageUrl().isEmpty()) {
            com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mBuilder.setLargeIcon(bitmap);

                    notificationManager.notify(
                            NOTIFICATION_ID,
                            mBuilder.build());
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.with(getApplicationContext())
                    .load(targetItem.getImageUrl())
                    .into(target);
        } else {
            notificationManager.notify(
                    NOTIFICATION_ID,
                    mBuilder.build());
        }
    }
}
