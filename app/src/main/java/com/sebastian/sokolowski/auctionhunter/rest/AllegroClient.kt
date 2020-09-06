package com.sebastian.sokolowski.auctionhunter.rest

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllegroClient(val context: Context) {
    private lateinit var allegroService: AllegroService

    fun getAllegroService(): AllegroService {
        if (!::allegroService.isInitialized) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(AllegroService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()

            allegroService = retrofit.create(AllegroService::class.java)
        }

        return allegroService
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }
}