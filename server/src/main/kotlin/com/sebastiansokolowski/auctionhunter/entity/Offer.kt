package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "offer")
data class Offer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var offer_id: String = "",
        var name: String = "",
        var price: Float = 0.0f,
        var url: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Offer

        if (offer_id != other.offer_id) return false

        return true
    }

    override fun hashCode(): Int {
        return offer_id.hashCode()
    }
}