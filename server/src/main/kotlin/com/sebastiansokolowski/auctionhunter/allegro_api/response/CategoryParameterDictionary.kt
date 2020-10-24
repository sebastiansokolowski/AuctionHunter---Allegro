package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class CategoryParameterDictionary (
        val id: String,
        val value: String,
        val dependsOnValueIds: List<String>
)