package com.sebastiansokolowski.auctionhunter.dao

import com.sebastiansokolowski.auctionhunter.entity.BlacklistUser
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface BlacklistUserDao : CrudRepository<BlacklistUser, Long> {
    fun existByUsername(username: String): Boolean
}