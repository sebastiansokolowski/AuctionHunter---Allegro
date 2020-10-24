package com.sebastiansokolowski.auctionhunter.allegro_api

import java.util.concurrent.TimeUnit

class SessionManager {

    private var userToken: String? = null
    private var userTokenExpiresTimestamp: Long? = null

    fun isAuthTokenValid(): Boolean {
        val expiresTimestamp = userTokenExpiresTimestamp ?: System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(60)
        return expiresTimestamp >= System.currentTimeMillis()
    }

    fun saveAuthToken(token: String, expiresInS: Long) {
        userToken = token
        userTokenExpiresTimestamp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresInS)
    }

    fun fetchAuthToken(): String? {
        return userToken
    }
}