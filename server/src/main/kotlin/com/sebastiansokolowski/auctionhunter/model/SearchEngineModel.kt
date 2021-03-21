package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.config.AuctionHunterProp
import com.sebastiansokolowski.auctionhunter.dao.BlacklistUserDao
import com.sebastiansokolowski.auctionhunter.dao.TargetDao
import com.sebastiansokolowski.auctionhunter.entity.Offer
import com.sebastiansokolowski.auctionhunter.entity.Target
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import kotlin.random.Random

class SearchEngineModel {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var mailSenderModel: MailSenderModel

    @Autowired
    private lateinit var telegramBotModel: TelegramBotModel

    @Autowired
    private lateinit var targetDao: TargetDao

    @Autowired
    private lateinit var blacklistUserDao: BlacklistUserDao

    @Autowired
    private lateinit var auctionHunterProp: AuctionHunterProp

    @Autowired
    private lateinit var websiteParserModel: WebsiteParserModel

    fun startSearching() {
        val timer = Timer()
        timer.scheduleAtFixedRate(
                SearchTask(),
                0,
                TimeUnit.SECONDS.toMillis(auctionHunterProp.searchPeriodInS.toLong())
        )
    }

    inner class SearchTask : TimerTask() {
        override fun run() {
            if (!isNightTime()) {
                search()
            }
        }

        override fun scheduledExecutionTime(): Long {
            return super.scheduledExecutionTime()
        }

        override fun cancel(): Boolean {
            return super.cancel()
        }
    }

    private fun isNightTime(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        auctionHunterProp.nightMode?.run {
            if (hour in start..stop) {
                LOG.info("night mode on")
                return true
            }
        }
        return false
    }

    private fun search() {
        targetDao.findAll().forEach { target ->
            val url = getTargetUrl(target)
            websiteParserModel.getOffers(url, object : WebsiteParserModel.WebsiteParserCallback {
                override fun onFailure(throwable: Throwable) {
                    LOG.info("EXCEPTION websiteParserModel.getOffers $throwable")
                }

                override fun onSuccess(offers: MutableList<Offer>) {
                    checkNewOffers(target, offers)
                }
            })

            Thread.sleep(Random.nextLong(500))
        }
    }

    private fun getTargetUrl(target: Target):String {
        var url = "https://allegro.pl/"
        val seller = target.parameters.find { it.name == "seller.login" }
        url += if (seller != null) {
            "uzytkownik/${seller.value}/${target.categoryId}?"
        } else {
            "kategoria/${target.categoryId}?"
        }
        url += "&order=d"
        if (!target.phrase.isNullOrEmpty()) {
            url += "&string=${target.phrase}".replace(" ", "%20")
        }
        target.parameters.forEach { parameter ->
            if (parameter.name == "price.from") {
                url += "&price.from=${parameter.value}"
            }
            if (parameter.name == "price.to") {
                url += "&price.to=${parameter.value}"
            }
            if (parameter.name == "sellingMode.format" && parameter.value == "BUY_NOW") {
                url += "&offerTypeBuyNow=1"
            }
            if (parameter.name == "searchMode" && parameter.value == "DESCRIPTIONS") {
                url += "&description=1"
            }
        }

        return url
    }

    private fun checkNewOffers(target: Target, offers: MutableList<Offer>) {
        var filteredOffers = offers
        filteredOffers = filterOldOffers(target, filteredOffers)
        filteredOffers = filterIncludeKeywords(target, filteredOffers)
        filteredOffers = filterExcludeKeywords(target, filteredOffers)
        //TODO
        //filteredOffers = filterBlacklistUsers(filteredOffers)

        target.offers.addAll(filteredOffers)

        if (filteredOffers.isNotEmpty()) {
            filteredOffers.sortBy { it.price }

            LOG.info("new offers! offersIds=${filteredOffers.map { it.id }}")
            if (auctionHunterProp.email.isNotEmpty()) {
                mailSenderModel.sendMail(filteredOffers)
            }
            if (auctionHunterProp.telegramBot != null) {
                telegramBotModel.sendMessage(filteredOffers)
            }
            targetDao.save(target)
        }
    }

    private fun filterOldOffers(target: Target, offers: MutableList<Offer>): MutableList<Offer> {
        val oldOffers = target.offers.map { it.offer_id }
        return offers.filterNot { oldOffers.contains(it.offer_id) }.toMutableList()
    }

    private fun filterBlacklistUsers(offers: MutableList<Offer>): MutableList<Offer> {
        val matchedOffers = mutableListOf<Offer>()
//        val blacklistUsers = blacklistUserDao.findAll()
//
//        offers.forEach { offer ->
//            blacklistUsers.forEach blacklist_loop@{ blacklistUser ->
//                val sellerLogin = offer.seller.login ?: return@blacklist_loop
//                if (blacklistUser.regex && sellerLogin.matches(Regex(blacklistUser.username)) ||
//                        sellerLogin == blacklistUser.username) {
//                    matchedOffers.add(offer)
//                }
//            }
//        }

        return offers.minus(matchedOffers).toMutableList()
    }

    private fun filterIncludeKeywords(target: Target, offers: MutableList<Offer>): MutableList<Offer> {
        val matchedOffers = mutableListOf<Offer>()

        val includeKeywords = target.keywords.filter { it.include }.map { it.phrase }
        if (includeKeywords.isNotEmpty()) {
            offers.forEach { offer ->
                includeKeywords.forEach { includeKeyword ->
                    if (offer.name.contains(includeKeyword, ignoreCase = true)) {
                        matchedOffers.add(offer)
                    }
                }
            }
        } else {
            matchedOffers.addAll(offers)
        }
        return matchedOffers
    }

    private fun filterExcludeKeywords(target: Target, offers: MutableList<Offer>): MutableList<Offer> {
        val matchedOffers = mutableListOf<Offer>()

        val excludeKeywords = target.keywords.filter { !it.include }.map { it.phrase }
        if (excludeKeywords.isNotEmpty()) {
            offers.forEach { offer ->
                excludeKeywords.forEach { excludeKeyword ->
                    if (!offer.name.contains(excludeKeyword, ignoreCase = true)) {
                        matchedOffers.add(offer)
                    }
                }
            }
        } else {
            matchedOffers.addAll(offers)
        }

        return matchedOffers
    }
}