package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class OfferSeller (
        val id: String,
        val login: String?,
        val company: Boolean,
        val superSeller: Boolean
)