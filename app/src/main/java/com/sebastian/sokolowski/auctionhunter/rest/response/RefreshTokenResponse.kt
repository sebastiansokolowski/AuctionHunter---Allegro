package com.sebastian.sokolowski.auctionhunter.rest.response

data class RefreshTokenResponse(
        val access_token: String,
        val token_type: String,
        val refresh_token: String,
        val expires_in: Long,
        val allegro_api: Boolean,
        val jti: String
)