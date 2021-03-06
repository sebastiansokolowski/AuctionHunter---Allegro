package com.sebastian.sokolowski.auctionhunter.rest.response

data class ListingOffer @JvmOverloads constructor(
        val id: String,
        val name: String,
        val delivery: OfferDelivery,
        val images: List<OfferImages>,
        val sellingMode: OfferSellingMode,
        val vendor: OfferVendor?,
        val category: OfferCategory
)