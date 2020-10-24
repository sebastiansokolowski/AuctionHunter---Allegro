package com.sebastiansokolowski.auctionhunter.allegro_api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllegroClient {

    @Value("\${CLIENT_ID}")
    private lateinit var clientId: String

    @Value("\${CLIENT_SECRET}")
    private lateinit var clientSecret: String

    private lateinit var allegroApi: AllegroApi

    fun getAllegroApi(): AllegroApi {
        if (!::allegroApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(AllegroApi.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()

            allegroApi = retrofit.create(AllegroApi::class.java)
        }

        return allegroApi
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(clientId, clientSecret))
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

}