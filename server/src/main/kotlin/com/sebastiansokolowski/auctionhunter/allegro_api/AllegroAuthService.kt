package com.sebastiansokolowski.auctionhunter.allegro_api

import com.sebastiansokolowski.auctionhunter.allegro_api.response.AuthResponse
import com.sebastiansokolowski.auctionhunter.allegro_api.response.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AllegroAuthService {
    companion object {
        const val baseUrl = "https://allegro.pl"
    }

    @POST("/auth/oauth/token")
    fun auth(@Header("Authorization") auth: String,
             @Query("grant_type") grantType: String): Call<AuthResponse>

    @POST("/auth/oauth/token")
    fun refreshToken(@Header("Authorization") auth: String,
                     @Query("grant_type") grantType: String,
                     @Query("refresh_token") refresh_token: String): Call<RefreshTokenResponse>

}