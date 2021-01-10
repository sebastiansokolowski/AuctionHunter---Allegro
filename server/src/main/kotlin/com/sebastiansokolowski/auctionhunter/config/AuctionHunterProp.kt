package com.sebastiansokolowski.auctionhunter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "auction-hunter")
data class AuctionHunterProp(
        var email: String = "",
        var searchPeriodInS: Number = 10,
        var allegroShowItemBaseUrl: String = ""
)