package com.sebastian.sokolowski.auctionhunter.rest

import android.content.Context
import android.content.SharedPreferences
import com.sebastian.sokolowski.auctionhunter.R
import java.util.concurrent.TimeUnit

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_TOKEN_EXPIRES_TIMESTAMP = "user_token_expires_timestamp"
    }

    fun isAuthTokenValid(): Boolean {
        val expiresTimestamp = prefs.getLong(USER_TOKEN_EXPIRES_TIMESTAMP, System.currentTimeMillis()) -
                TimeUnit.SECONDS.toMillis(60)

        return expiresTimestamp >= System.currentTimeMillis()
    }

    fun saveAuthToken(token: String, expiresInS: Long) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putLong(USER_TOKEN_EXPIRES_TIMESTAMP, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresInS))
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}