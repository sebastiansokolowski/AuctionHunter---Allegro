package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Target(
        @Id
        var id: Long = 0,
        var name: String = ""
)