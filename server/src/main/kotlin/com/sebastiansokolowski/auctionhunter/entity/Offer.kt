package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "offer")
data class Offer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var offer_id: String = ""
)