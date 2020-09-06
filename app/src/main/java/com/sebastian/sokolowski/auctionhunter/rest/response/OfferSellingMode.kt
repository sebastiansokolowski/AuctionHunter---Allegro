package com.sebastian.sokolowski.auctionhunter.rest.response

data class OfferSellingMode @JvmOverloads constructor(
    val format: SellingModeFormat,
    val price: OfferPrice,
    val fixedPrice: OfferFixedPrice,
    val popularity: Int,
    val bidCount: Int
)