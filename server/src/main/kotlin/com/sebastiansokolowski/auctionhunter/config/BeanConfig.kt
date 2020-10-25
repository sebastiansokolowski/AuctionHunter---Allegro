package com.sebastiansokolowski.auctionhunter.config

import com.sebastiansokolowski.auctionhunter.allegro_api.AllegroClient
import com.sebastiansokolowski.auctionhunter.model.MailSenderModel
import com.sebastiansokolowski.auctionhunter.model.SearchEngineModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {
    @Bean
    fun getAllegroClient() = AllegroClient()

    @Bean
    fun getSendMailModel() = MailSenderModel()

    @Bean
    fun getSearchEngine() = SearchEngineModel()
}