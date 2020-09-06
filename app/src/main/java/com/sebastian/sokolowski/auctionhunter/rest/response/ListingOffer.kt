package com.sebastian.sokolowski.auctionhunter.rest.response

data class ListingOffer @JvmOverloads constructor(
        val id: String,
        val name: String,
        val delivery: OfferDelivery,
        val images: OfferImages,
        val sellingMode: OfferSellingMode,
        val category: OfferCategory
)