package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class OfferDelivery (
        val availableForFree: Boolean,
        val lowestPrice: OfferLowestPrice
)