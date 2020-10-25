package com.sebastiansokolowski.auctionhunter.dao

import com.sebastiansokolowski.auctionhunter.entity.TargetParameters
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface TargetParametersDao : CrudRepository<TargetParameters, Long>