package com.sebastian.sokolowski.auctionhunter.rest

import android.content.Context
import android.util.Base64
import com.sebastian.sokolowski.auctionhunter.BuildConfig
import com.sebastian.sokolowski.auctionhunter.rest.response.AuthResponse
import com.sebastian.sokolowski.auctionhunter.rest.response.RefreshTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllegroAuthClient(val context: Context) {
    private lateinit var allegroAuthService: AllegroAuthService

    fun auth(): Call<AuthResponse> {
        val allegroAuthService = getAllegroAuthService()
        return allegroAuthService.auth(getAuthorizationHeader(), "client_credentials")
    }

    fun refresh(token: String): Call<RefreshTokenResponse> {
        val allegroAuthService = getAllegroAuthService()
        return allegroAuthService.refreshToken(getAuthorizationHeader(), "refresh_token", token)
    }

    private fun getAuthorizationHeader(): String {
        val credential: String = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET
        return "Basic " + Base64.encodeToString(credential.toByteArray(), Base64.NO_WRAP)
    }

    private fun getAllegroAuthService(): AllegroAuthService {
        if (!::allegroAuthService.isInitialized) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(AllegroAuthService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()

            allegroAuthService = retrofit.create(AllegroAuthService::class.java)
        }

        return allegroAuthService
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }
}