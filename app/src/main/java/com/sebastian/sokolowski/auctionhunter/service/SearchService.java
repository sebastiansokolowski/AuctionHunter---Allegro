package com.sebastian.sokolowski.auctionhunter.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.helper.FilterHelper;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.main.MainActivity;
import com.sebastian.sokolowski.auctionhunter.rest.AllegroClient;
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing;
import com.sebastian.sokolowski.auctionhunter.utils.MyUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
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
    private final static int FOREGROUND_NOTIFICATION_ID = 1;
    private final static String NOTIFICATION__CHANNEL_ID = "default_channel";
    private static int NOTIFICATION_ID = 2;

    private Realm mRealm = Realm.getDefaultInstance();
    private SharedPreferences mSharedPreferences;
    private AllegroClient allegroClient;


    private long searchingInterval;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        allegroClient = new AllegroClient(this);
        //TODO: change share pref name
        searchingInterval = mSharedPreferences.getLong("searching_interval", TimeUnit.SECONDS.toMillis(10));

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

        updateForegroundNotificationText();

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
                        for (TargetItem savedTargetItem : target.getAllItems()) {
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION__CHANNEL_ID, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createForegroundNotification(Date lastScanDate) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String lastScanTimeText = "null";
        if (lastScanDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            lastScanTimeText = simpleDateFormat.format(lastScanDate);
        }

        return new Notification.Builder(this, NOTIFICATION__CHANNEL_ID)
                .setContentTitle(getString(R.string.foreground_notification_title))
                .setContentText(getString(R.string.foreground_notification_text, lastScanTimeText))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .build();
    }

    private void showForegroundNotification() {
        Notification notification = createForegroundNotification(null);
        startForeground(FOREGROUND_NOTIFICATION_ID, notification);
    }

    private void updateForegroundNotificationText() {
        Notification notification = createForegroundNotification(new Date());

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(FOREGROUND_NOTIFICATION_ID, notification);
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

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION__CHANNEL_ID)
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
