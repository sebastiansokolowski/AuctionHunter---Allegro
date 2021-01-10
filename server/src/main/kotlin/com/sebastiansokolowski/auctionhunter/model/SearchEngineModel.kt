package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.allegro_api.AllegroClient
import com.sebastiansokolowski.auctionhunter.allegro_api.response.Listing
import com.sebastiansokolowski.auctionhunter.allegro_api.response.ListingOffer
import com.sebastiansokolowski.auctionhunter.dao.BlacklistUserDao
import com.sebastiansokolowski.auctionhunter.dao.TargetDao
import com.sebastiansokolowski.auctionhunter.entity.Offer
import com.sebastiansokolowski.auctionhunter.entity.Target
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import kotlin.collections.HashMap

class SearchEngineModel {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var allegroClient: AllegroClient

    @Autowired
    private lateinit var mailSenderModel: MailSenderModel

    @Autowired
    private lateinit var targetDao: TargetDao

    @Autowired
    private lateinit var blacklistUserDao: BlacklistUserDao

    @Value("\${SEARCH_PERIOD_IN_S}")
    private lateinit var searchPeriodInS: Number

    fun startSearching() {
        val timer = Timer()
        timer.scheduleAtFixedRate(
                SearchTask(),
                0,
                TimeUnit.SECONDS.toMillis(searchPeriodInS.toLong())
        )
    }

    inner class SearchTask : TimerTask() {
        override fun run() {
            search()
        }

        override fun scheduledExecutionTime(): Long {
            return super.scheduledExecutionTime()
        }

        override fun cancel(): Boolean {
            return super.cancel()
        }
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
        val offersToNotify = mutableListOf<ListingOffer>()

        val oldOffers = target.offers.map { it.offer_id }
        val newOffers = offers.filterNot { it.seller.login != null && blacklistUserDao.existsByUsername(it.seller.login) }
        newOffers.forEach { newOffer ->
            if (!oldOffers.contains(newOffer.id)) {
                offersToNotify.add(newOffer)
                target.offers.add(Offer(offer_id = newOffer.id))
            }
        }

        if (offersToNotify.isNotEmpty()) {
            offersToNotify.sortBy { it.sellingMode.price.amount }

            LOG.info("new offers! offersIds=${offersToNotify.map { it.id }}")
            mailSenderModel.sendMail(offersToNotify)
            targetDao.save(target)
        }
    }
}