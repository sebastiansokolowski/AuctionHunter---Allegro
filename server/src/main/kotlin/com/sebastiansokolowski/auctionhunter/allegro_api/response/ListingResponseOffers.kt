package com.sebastiansokolowski.auctionhunter.allegro_api.response

data class ListingResponseOffers (
        val promoted: List<ListingOffer>,
        val regular: List<ListingOffer>
)