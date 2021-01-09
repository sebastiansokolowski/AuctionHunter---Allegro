package com.sebastiansokolowski.auctionhunter.controller

import com.sebastiansokolowski.auctionhunter.config.ServiceProperties
import com.sebastiansokolowski.auctionhunter.dao.BlacklistUserDao
import com.sebastiansokolowski.auctionhunter.entity.BlacklistUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@CrossOrigin
@RequestMapping("/blacklist_user")
class BlacklistController {
    private val LOG = Logger.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var serviceProperties: ServiceProperties

    @Autowired
    private lateinit var blacklistUserDao: BlacklistUserDao

    @GetMapping
    fun getBlacklistUsers(): List<BlacklistUser> {
        return blacklistUserDao.findAll().toList()
    }

    @PostMapping
    fun createBlacklistUser(@RequestBody blacklistUser: BlacklistUser): BlacklistUser {
        return blacklistUserDao.save(blacklistUser)
    }

    @GetMapping("/{id}")
    fun getBlacklistUser(@PathVariable("id") id: Long): ResponseEntity<BlacklistUser> {
        return blacklistUserDao.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteTarget(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return blacklistUserDao.findById(id).map {
            blacklistUserDao.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}

