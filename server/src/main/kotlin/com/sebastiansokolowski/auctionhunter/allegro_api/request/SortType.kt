package com.sebastiansokolowski.auctionhunter.allegro_api.request

/**
 * Created by Sebastian Sokołowski on 22.09.20.
 */
enum class SortType(val id: String) {
    RELEVANCE_DESC("-relevance"),
    PRICE_ASC("+price"),
    PRICE_DESC("-price"),
    PRICE_WITH_DELIVERY_ASC("+withDeliveryPrice"),
    PRICE_WITH_DELIVERY_DESC("-withDeliveryPrice"),
    POPULARITY_DESC("-popularity"),
    END_TIME_ASC("+endTime"),
    START_TIME_DESC("-startTime")
}