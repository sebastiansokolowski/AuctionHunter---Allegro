package com.sebastian.sokolowski.auctionhunter.rest.response

data class CategoryDto @JvmOverloads constructor(
        val id: String,
        val leaf: Boolean,
        val name: String,
        val parent: CategoryDto
)