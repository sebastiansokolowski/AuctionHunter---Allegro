package com.sebastian.sokolowski.auctionhunter.rest.response

data class OfferSeller @JvmOverloads constructor(
        val id: String,
        val login: String,
        val company: Boolean,
        val superSeller: Boolean
)