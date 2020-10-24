package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class OfferSellingMode (
    val format: SellingModeFormat,
    val price: OfferPrice,
    val fixedPrice: OfferFixedPrice?,
    val popularity: Int,
    val bidCount: Int
)