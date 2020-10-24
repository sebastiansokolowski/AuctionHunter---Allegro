package com.sebastiansokolowski.auctionhunter.dao

import com.sebastiansokolowski.auctionhunter.entity.Target
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface TargetDao : CrudRepository<Target, Long>