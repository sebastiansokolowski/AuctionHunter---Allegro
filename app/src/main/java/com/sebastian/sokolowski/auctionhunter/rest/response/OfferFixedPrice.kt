package com.sebastian.sokolowski.auctionhunter.rest.response

data class OfferFixedPrice @JvmOverloads constructor(
        val amount: String,
        val currency: String
)