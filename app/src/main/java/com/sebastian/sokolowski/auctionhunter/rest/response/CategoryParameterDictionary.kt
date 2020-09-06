package com.sebastian.sokolowski.auctionhunter.rest.response

data class CategoryParameterDictionary @JvmOverloads constructor(
        val id: String,
        val value: String,
        val dependsOnValueIds: List<String>
)