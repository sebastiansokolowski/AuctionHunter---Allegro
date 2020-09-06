package com.sebastian.sokolowski.auctionhunter.rest.response

data class OfferLowestPrice @JvmOverloads constructor(
        val amount: String,
        val currency: String
)