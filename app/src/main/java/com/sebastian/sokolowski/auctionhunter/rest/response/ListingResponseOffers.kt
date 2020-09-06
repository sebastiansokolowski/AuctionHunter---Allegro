package com.sebastian.sokolowski.auctionhunter.rest.response

data class ListingResponseOffers @JvmOverloads constructor(
        val promoted: List<ListingOffer>,
        val regular: List<ListingOffer>
)