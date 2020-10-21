package com.sebastian.sokolowski.auctionhunter.utils;

import android.content.Context;
import android.view.animation.Animation;

import com.sebastian.sokolowski.auctionhunter.R;

import java.text.DecimalFormat;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class MyUtils {

    public static Animation createRotateAnimation(Context context) {
        Animation loadingAnimation = android.view.animation.AnimationUtils.loadAnimation(context,
                R.anim.progress_anim);
        loadingAnimation.setDuration(1000);
        loadingAnimation.setRepeatMode(Animation.INFINITE);
        loadingAnimation.setRepeatCount(Animation.INFINITE);
        return loadingAnimation;
    }

    public static String getPrice(Context context, Float price) {
        if (price == null) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(price) + " " + context.getString(R.string.currency_name);
    }
}
