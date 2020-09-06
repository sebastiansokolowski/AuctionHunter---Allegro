package com.sebastian.sokolowski.auctionhunter.rest

import android.content.Context
import com.sebastian.sokolowski.auctionhunter.rest.response.AuthResponse
import com.sebastian.sokolowski.auctionhunter.rest.response.RefreshTokenResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)
    private val allegroAuthClient = AllegroAuthClient(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (sessionManager.isAuthTokenValid()) {
            sessionManager.fetchAuthToken()?.let {
                setAuthHeader(requestBuilder, it)
            }
        } else {
            var token: String? = null
            var expiresIn: Long? = null

            //oauth token-12 hours validity
            val authResponse = auth()
            if (authResponse != null) {
                token = authResponse.access_token
                expiresIn = authResponse.expires_in
            }
            //refresh token-3 months validity
            if (token != null) {
                val refreshTokenResponse = refreshToken(token)
                if (refreshTokenResponse != null) {
                    token = refreshTokenResponse.refresh_token
                    expiresIn = refreshTokenResponse.expires_in
                }
            }

            if (token != null && expiresIn != null) {
                sessionManager.saveAuthToken(token, expiresIn)
                setAuthHeader(requestBuilder, token)
            }
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun setAuthHeader(requestBuilder: Request.Builder, token: String) {
        requestBuilder.addHeader("Authorization", "Bearer $token")
    }

    private fun auth(): AuthResponse? {
        val response = allegroAuthClient.auth().execute()
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

    private fun refreshToken(token: String): RefreshTokenResponse? {
        val response = allegroAuthClient.refresh(token).execute()
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }
}