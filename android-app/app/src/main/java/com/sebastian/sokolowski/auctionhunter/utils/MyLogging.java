package com.sebastian.sokolowski.auctionhunter.utils;

import android.os.Environment;

import com.sebastian.sokolowski.auctionhunter.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sebastian Sokołowski on 12.03.17.
 */

public class MyLogging {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
    private static String LOG_FOLDER = "AuctionHunter_logs";

    public static void loggingToStorage() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        try {
            final File path = new File(
                    Environment.getExternalStorageDirectory(), LOG_FOLDER);
            if (!path.exists()) {
                path.mkdir();
            }
            Runtime.getRuntime().exec(
                    "logcat  -d -f " + path + File.separator
                            + "logcat_"
                            + dateFormat.format(new Date())
                            + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
