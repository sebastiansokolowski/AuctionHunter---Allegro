package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.config.AuctionHunterProp
import com.sebastiansokolowski.auctionhunter.entity.Offer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.logging.Logger

class TelegramBotModel {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    interface TelegramAPI {
        companion object {
            const val baseUrl = "https://api.telegram.org"
        }

        @GET("/bot{token}/sendMessage")
        fun sendMessage(@Path("token") token: String,
                        @Query("chat_id") chatId: String,
                        @Query("text") text: String,
                        @Query("parse_mode") parseMode: String = "html"): Call<Any>
    }

    @Autowired
    private lateinit var auctionHunterProp: AuctionHunterProp

    private lateinit var telegramApi: TelegramAPI

    private fun getTelegramApi(): TelegramAPI {
        if (!::telegramApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(TelegramAPI.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()

            telegramApi = retrofit.create(TelegramAPI::class.java)
        }

        return telegramApi
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    fun sendMessage(offers: List<Offer>) {
        offers.forEach { offer ->
            var message = ""
            //1st line
            message += offer.name + "\n"
            message += "<b>${offer.price} zł</b>" + "\n"
            //2th line
            message += offer.url

            //send
            auctionHunterProp.telegramBot?.let { prop ->
                getTelegramApi().sendMessage(prop.token, prop.chatId, message).enqueue(object : retrofit2.Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        LOG.info(t.message)
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    }
                })
            }
        }
    }

}