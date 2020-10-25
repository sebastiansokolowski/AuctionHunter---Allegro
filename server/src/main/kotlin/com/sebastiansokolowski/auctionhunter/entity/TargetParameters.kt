package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "target_parameters")
data class TargetParameters(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var name: String = "",
        var value: String = ""
)