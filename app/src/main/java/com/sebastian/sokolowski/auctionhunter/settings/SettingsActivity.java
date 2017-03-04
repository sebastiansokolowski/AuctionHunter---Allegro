package com.sebastian.sokolowski.auctionhunter.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.sebastian.sokolowski.auctionhunter.R;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);
    }
}
