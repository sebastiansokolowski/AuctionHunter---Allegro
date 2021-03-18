package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.allegro_api.AllegroClient
import com.sebastiansokolowski.auctionhunter.allegro_api.response.Listing
import com.sebastiansokolowski.auctionhunter.allegro_api.response.ListingOffer
import com.sebastiansokolowski.auctionhunter.config.AuctionHunterProp
import com.sebastiansokolowski.auctionhunter.dao.BlacklistUserDao
import com.sebastiansokolowski.auctionhunter.dao.TargetDao
import com.sebastiansokolowski.auctionhunter.entity.Offer
import com.sebastiansokolowski.auctionhunter.entity.Target
import org.springframework.beans.factory.annotation.Autowired
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import kotlin.collections.HashMap
import kotlin.collections.set

class SearchEngineModel {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var allegroClient: AllegroClient

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
            val parameters = HashMap<String, String>()
            target.parameters.forEach { parameter ->
                parameters[parameter.name] = parameter.value
            }

            allegroClient.getAllegroApi().getOffers(
                    target.categoryId,
                    target.phrase,
                    parameters = parameters
            ).enqueue(object : Callback<Listing> {
                override fun onFailure(call: Call<Listing>, t: Throwable) {
                    LOG.info(t.message)
                }

                override fun onResponse(call: Call<Listing>, response: Response<Listing>) {
                    if (response.body() != null) {
                        val offers = mutableListOf<ListingOffer>()
                        offers.addAll(response.body()!!.items.promoted)
                        offers.addAll(response.body()!!.items.regular)
                        checkNewOffers(target, offers)
                    } else {
                        LOG.info("response body is null")
                    }
                }
            })
        }
    }

    private fun checkNewOffers(target: Target, offers: MutableList<ListingOffer>) {
        var filteredOffers = offers
        filteredOffers = filterOldOffers(target, filteredOffers)
        filteredOffers = filterIncludeKeywords(target, filteredOffers)
        filteredOffers = filterExcludeKeywords(target, filteredOffers)
        filteredOffers = filterBlacklistUsers(filteredOffers)

        target.offers.addAll(filteredOffers.map { Offer(offer_id = it.id) })

        if (filteredOffers.isNotEmpty()) {
            filteredOffers.sortBy { it.sellingMode.price.amount }

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

    private fun filterOldOffers(target: Target, offers: MutableList<ListingOffer>): MutableList<ListingOffer> {
        val oldOffers = target.offers.map { it.offer_id }
        return offers.filterNot { oldOffers.contains(it.id) }.toMutableList()
    }

    private fun filterBlacklistUsers(offers: MutableList<ListingOffer>): MutableList<ListingOffer> {
        val matchedOffers = mutableListOf<ListingOffer>()
        val blacklistUsers = blacklistUserDao.findAll()

        offers.forEach { offer ->
            blacklistUsers.forEach blacklist_loop@{ blacklistUser ->
                val sellerLogin = offer.seller.login ?: return@blacklist_loop
                if (blacklistUser.regex && sellerLogin.matches(Regex(blacklistUser.username)) ||
                        sellerLogin == blacklistUser.username) {
                    matchedOffers.add(offer)
                }
            }
        }

        return offers.minus(matchedOffers).toMutableList()
    }

    private fun filterIncludeKeywords(target: Target, offers: MutableList<ListingOffer>): MutableList<ListingOffer> {
        val matchedOffers = mutableListOf<ListingOffer>()

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

    private fun filterExcludeKeywords(target: Target, offers: MutableList<ListingOffer>): MutableList<ListingOffer> {
        val matchedOffers = mutableListOf<ListingOffer>()

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