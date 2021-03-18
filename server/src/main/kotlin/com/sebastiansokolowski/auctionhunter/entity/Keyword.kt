package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "keyword")
data class Keyword(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var phrase: String = "",
        var include: Boolean = false
)