package com.sebastian.sokolowski.auctionhunter.rest

import android.content.Context
import com.sebastian.sokolowski.auctionhunter.database.models.Target
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameterType
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
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

    fun getOffers(target: Target): Call<Listing> {
        val allegroService = getAllegroService()

        val categoryId = target.categoryId
        val phrase = target.searchingName
        val parameters: HashMap<String, String> = HashMap()

        if (target.filterModels != null && target.filterModels.size != 0) {
            for (filterModel in target.filterModels) {

                val parameterKey = if (filterModel.isParameter) {
                    "parameter.${filterModel.filterId}"
                } else {
                    filterModel.filterId
                }

                when (filterModel.dataTypeEnum) {
                    CategoryParameterType.DICTIONARY -> {
                        if (filterModel.isMultipleChoices) {
                            filterModel.filterValueIdList.forEach {
                                if (!it.string.isNullOrEmpty()) {
                                    parameters.put(parameterKey, it.string)
                                }
                            }
                        } else {
                            val value = filterModel.filterValueIdList.firstOrNull()?.string
                            if (!value.isNullOrEmpty()) {
                                parameters.put(parameterKey, value)
                            }
                        }
                    }
                    CategoryParameterType.INTEGER,
                    CategoryParameterType.FLOAT,
                    CategoryParameterType.STRING -> {
                        if (filterModel.isRange) {
                            val min = filterModel.filterValueIdRange.rangeValueMin
                            val max = filterModel.filterValueIdRange.rangeValueMax
                            if (!min.isNullOrEmpty()) {
                                parameters.put("$parameterKey.from", min)
                            }
                            if (!max.isNullOrEmpty()) {
                                parameters.put("$parameterKey.to", max)
                            }
                        } else {
                            val value = filterModel.filterValueIdList.firstOrNull()?.string
                            if (!value.isNullOrEmpty()) {
                                parameters.put(parameterKey, value)
                            }
                        }
                    }
                    else -> {
                    }
                }
            }
        }


        return allegroService.getOffers(categoryId, phrase, parameters = parameters)
    }
}