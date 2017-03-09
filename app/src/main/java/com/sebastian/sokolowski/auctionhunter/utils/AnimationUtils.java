package com.sebastian.sokolowski.auctionhunter.utils;

import android.content.Context;
import android.view.animation.Animation;

import com.sebastian.sokolowski.auctionhunter.R;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class AnimationUtils {

    public static Animation createRotateAnimation(Context context) {
        Animation loadingAnimation = android.view.animation.AnimationUtils.loadAnimation(context,
                R.anim.progress_anim);
        loadingAnimation.setDuration(1000);
        loadingAnimation.setRepeatMode(Animation.INFINITE);
        loadingAnimation.setRepeatCount(Animation.INFINITE);
        return loadingAnimation;
    }
}
