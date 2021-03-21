package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.entity.Offer
import com.sebastiansokolowski.auctionhunter.website_parser.AllegroWebsiteParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.logging.Logger
import kotlin.random.Random


class WebsiteParserModel {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    var executor: ExecutorService = Executors.newCachedThreadPool()

    private val userAgents = arrayOf(
            "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    )
    private val userReferrers = arrayOf(
            "http://www.google.com",
            "http://www.google.pl",
            "https://www.bing.com/",
            "https://www.yahoo.com/",
            "https://duckduckgo.com/"
    )

    private val parsers = arrayOf(
            //TODO:
    )

    fun getOffers(url: String, callback: WebsiteParserCallback) {
        val siteParser = getParser(url)
        if (siteParser == null) {
            callback.onFailure(IllegalStateException("Parser not implemented yet. url=$url"))
            return
        }

        val agent = userAgents[Random.nextInt(userAgents.size)]
        val referrer = userReferrers[Random.nextInt(userReferrers.size)]

        Thread.sleep(Random.nextLong(500))

        executor.submit {
            try {
                val doc = Jsoup.connect(url)
                        .userAgent(agent)
                        .referrer(referrer)
                        .get()
                val offers = siteParser.parseOffers(doc)
                LOG.info("offers=${offers.size} url=$url")
                callback.onSuccess(offers)
            } catch (e: Throwable) {
                LOG.info(e.toString())
                callback.onFailure(e)
            }
        }
    }

    private fun getParser(url: String): WebsiteParserEngine? {
        return parsers.find { url.contains(it.websiteBaseUrl()) }
    }

    interface WebsiteParserCallback {
        fun onFailure(throwable: Throwable)

        fun onSuccess(offers: MutableList<Offer>)
    }

    interface WebsiteParserEngine {
        fun websiteBaseUrl(): String

        fun parseOffers(doc: Document): MutableList<Offer>
    }
}