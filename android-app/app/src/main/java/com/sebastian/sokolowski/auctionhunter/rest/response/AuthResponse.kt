package com.sebastian.sokolowski.auctionhunter.rest.response

data class AuthResponse(
        val access_token: String,
        val token_type: String,
        val expires_in: Long,
        val scope: String,
        val allegro_api: Boolean,
        val jti: String
)