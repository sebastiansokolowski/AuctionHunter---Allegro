package com.sebastiansokolowski.auctionhunter.entity

import javax.persistence.*

@Entity
@Table(name = "target")
data class Target(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var name: String = "",
        var categoryId: String = "",
        var phrase: String = "",
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "target_id")
        var parameters: MutableList<TargetParameters> = mutableListOf(),
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "target_id")
        var offers: MutableList<Offer> = mutableListOf()
)