package com.sebastiansokolowski.auctionhunter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "auction-hunter")
data class AuctionHunterProp(
        var email: String = "",
        var telegramBot: TelegramBotProp? = null,
        var searchPeriodInS: Int = 10,
        var allegroShowItemBaseUrl: String = "",
        var nightMode: NightModeProp? = null
)

data class NightModeProp(
        var start: Int = 0,
        var stop: Int = 0
)

data class TelegramBotProp(
        var token: String = "",
        var chatId: String = ""
)