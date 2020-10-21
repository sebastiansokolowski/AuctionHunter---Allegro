package com.sebastian.sokolowski.auctionhunter.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sebastian.sokolowski.auctionhunter.R

/**
 * Created by Sebastian Sokołowski on 22.09.20.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.activity_settings, rootKey)
    }
}