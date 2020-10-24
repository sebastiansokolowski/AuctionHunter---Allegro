package com.sebastiansokolowski.auctionhunter.allegro_api

import com.sebastiansokolowski.auctionhunter.allegro_api.response.AuthResponse
import com.sebastiansokolowski.auctionhunter.allegro_api.response.RefreshTokenResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(clientId: String, clientSecret: String) : Interceptor {

    private val sessionManager = SessionManager()
    private val allegroAuthClient = AllegroAuthClient(clientId, clientSecret)

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
        requestBuilder.addHeader("Accept", "application/vnd.allegro.public.v1+json")
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