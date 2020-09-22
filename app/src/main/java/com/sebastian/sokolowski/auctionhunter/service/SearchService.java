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
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroClient;
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing;
import com.sebastian.sokolowski.auctionhunter.soap.RequestManager;
import com.sebastian.sokolowski.auctionhunter.utils.MyUtils;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sebastian Sokołowski on 02.03.17.
 */

public class SearchService extends Service {
    private static String TAG = SearchService.class.getSimpleName();
    private static int NOTIFICATION_ID = 2;

    private final RequestManager mRequestManager = new RequestManager();
    private Realm mRealm = Realm.getDefaultInstance();
    private SharedPreferences mSharedPreferences;
    private AllegroClient allegroClient;


    private long searchingInterval;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        allegroClient = new AllegroClient(this);
        //TODO: change share pref name
        searchingInterval = mSharedPreferences.getLong("searching_interval", TimeUnit.MINUTES.toMillis(10));

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
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
            allegroClient.getOffers(target).enqueue(new Callback<Listing>() {
                @Override
                public void onResponse(Call<Listing> call, Response<Listing> response) {
                    List<TargetItem> targetItems = FilterHelper.createTargetItems(response.body());
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
                public void onFailure(Call<Listing> call, Throwable t) {
                    Log.e(TAG, "Target: " + target.toString());
                    Log.e(TAG, "onException: " + t.getMessage());
                }
            });
        }

    }

    private void showNotification(TargetItem targetItem) {
        NOTIFICATION_ID++;

        String title = "";
        switch (targetItem.getSettingModeFormat()) {
            case AUCTION:
                title = MyUtils.getPrice(getApplicationContext(), targetItem.getPriceBid());
                break;
            case ADVERTISEMENT:
            case BUY_NOW:
                title = MyUtils.getPrice(getApplicationContext(), targetItem.getPrice());
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
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
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
