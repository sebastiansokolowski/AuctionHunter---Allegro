package com.sebastian.sokolowski.auctionhunter.rest.response

data class OfferDelivery @JvmOverloads constructor(
        val availableForFree: Boolean,
        val lowestPrice: OfferLowestPrice
)