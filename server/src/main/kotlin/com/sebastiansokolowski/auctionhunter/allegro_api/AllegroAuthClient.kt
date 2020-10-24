package com.sebastiansokolowski.auctionhunter.allegro_api

import com.sebastiansokolowski.auctionhunter.allegro_api.response.AuthResponse
import com.sebastiansokolowski.auctionhunter.allegro_api.response.RefreshTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AllegroAuthClient(private val clientId: String, private val clientSecret: String) {

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
        val credential = "$clientId:$clientSecret"
        return "Basic " + String(Base64.getEncoder().encode(credential.toByteArray()))
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