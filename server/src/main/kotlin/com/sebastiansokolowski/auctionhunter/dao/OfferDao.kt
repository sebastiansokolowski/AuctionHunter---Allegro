package com.sebastiansokolowski.auctionhunter.dao

import com.sebastiansokolowski.auctionhunter.entity.Offer
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface OfferDao : CrudRepository<Offer, Long>