package com.sebastian.sokolowski.auctionhunter.rest

import com.sebastian.sokolowski.auctionhunter.rest.response.Categories
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryDto
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameters
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AllegroService {
    companion object {
        const val baseUrl = "https://api.allegro.pl"
    }

    @GET("/sale/categories")
    fun getCategories(@Query("parent.id") parentId: String): Call<Categories>

    @GET("/sale/categories/{id}")
    fun getCategory(@Path("id") id: Int): Call<CategoryDto>

    @GET("/sale/categories/{categoryId}/parameters")
    fun getCategoryParameters(@Path("categoryId") categoryId: String): Call<CategoryParameters>

    @GET("/offers/listing")
    fun getOffers(): Call<Listing>
}