package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "target")
data class Target(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var name: String? = null,
        var categoryId: String? = null,
        var phrase: String? = null,
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "target_id")
        var parameters: MutableList<TargetParameters> = mutableListOf(),
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "target_id")
        var offers: MutableList<Offer> = mutableListOf(),
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "target_id")
        var keywords: MutableList<Keyword> = mutableListOf()
)