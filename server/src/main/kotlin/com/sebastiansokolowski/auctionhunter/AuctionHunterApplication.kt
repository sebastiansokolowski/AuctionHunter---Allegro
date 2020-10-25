package com.sebastiansokolowski.auctionhunter

import com.sebastiansokolowski.auctionhunter.model.SearchEngineModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class AuctionHunterApplication {

    @Autowired
    private lateinit var searchEngineModel: SearchEngineModel

    @PostConstruct
    fun init() {
        searchEngineModel.startSearching()
    }
}

fun main(args: Array<String>) {
    runApplication<AuctionHunterApplication>(*args)
}
