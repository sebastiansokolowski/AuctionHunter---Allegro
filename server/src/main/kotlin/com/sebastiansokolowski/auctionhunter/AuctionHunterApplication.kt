package com.sebastiansokolowski.auctionhunter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuctionHunterApplication

fun main(args: Array<String>) {
	runApplication<AuctionHunterApplication>(*args)
}
