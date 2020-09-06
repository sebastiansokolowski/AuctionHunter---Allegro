package com.sebastian.sokolowski.auctionhunter.rest.response

data class ListingCategoryWithCount @JvmOverloads constructor(
        val id: String,
        val name: String,
        val count: Int
)