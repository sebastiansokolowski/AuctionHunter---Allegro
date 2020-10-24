package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class CategoryDto (
        val id: String,
        val leaf: Boolean,
        val name: String,
        val parent: CategoryDto
)