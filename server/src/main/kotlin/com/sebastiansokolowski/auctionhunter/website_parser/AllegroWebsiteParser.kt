package com.sebastiansokolowski.auctionhunter.website_parser

import com.sebastiansokolowski.auctionhunter.entity.Offer
import com.sebastiansokolowski.auctionhunter.model.WebsiteParserModel
import org.jsoup.nodes.Document

class AllegroWebsiteParser: WebsiteParserModel.WebsiteParserEngine {

    override fun websiteBaseUrl(): String = "allegro.pl"

    override fun parseOffers(doc: Document): MutableList<Offer> {
        val offers = mutableListOf<Offer>()

        val elements = doc.select("article").filterNot { it.attributes().hasKey("data-analytics-view-label") }
        elements.forEach { element ->
            val offerId = element.attr("data-analytics-view-value")
            val name = element.select("a._w7z6o").text()
            val price = element.select("span._1svub").text()
                    .filter { it.isDigit() }.toFloat()/100
            val offerUrl = element.select("a._w7z6o").attr("href")

            val offer = Offer(
                    offer_id = offerId,
                    name = name,
                    price = price,
                    url = offerUrl
            )
            offers.add(offer)
        }

        return offers
    }
}