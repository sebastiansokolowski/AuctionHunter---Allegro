package com.sebastian.sokolowski.auctionhunter.rest.response

data class Listing @JvmOverloads constructor(
        val items: List<ListingResponseOffers>,
        val categories: List<ListingResponseCategories>
)