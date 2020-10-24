package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class ListingResponseCategories (
        val subcategories: List<ListingCategoryWithCount>,
        val path: List<ListingCategory>
)