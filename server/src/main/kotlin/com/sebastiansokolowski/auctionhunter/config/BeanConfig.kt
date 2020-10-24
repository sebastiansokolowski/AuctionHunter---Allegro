package com.sebastiansokolowski.auctionhunter.config

import com.sebastiansokolowski.auctionhunter.allegro_api.AllegroClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {
    @Bean
    fun getAllegroClient() = AllegroClient()
}