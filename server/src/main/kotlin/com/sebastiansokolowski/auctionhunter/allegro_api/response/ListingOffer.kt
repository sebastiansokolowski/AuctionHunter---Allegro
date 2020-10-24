package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class ListingOffer (
        val id: String,
        val name: String,
        val delivery: OfferDelivery,
        val images: List<OfferImages>,
        val sellingMode: OfferSellingMode,
        val vendor: OfferVendor?,
        val category: OfferCategory
)