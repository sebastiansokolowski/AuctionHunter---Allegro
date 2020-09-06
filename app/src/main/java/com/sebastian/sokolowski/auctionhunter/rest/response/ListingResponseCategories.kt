package com.sebastian.sokolowski.auctionhunter.rest.response

data class ListingResponseCategories @JvmOverloads constructor(
        val subcategories: List<ListingCategoryWithCount>,
        val path: List<ListingCategory>
)