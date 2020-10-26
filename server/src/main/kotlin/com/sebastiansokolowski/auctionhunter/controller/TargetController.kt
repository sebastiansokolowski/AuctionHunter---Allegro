package com.sebastiansokolowski.auctionhunter.controller

import com.sebastiansokolowski.auctionhunter.dao.TargetDao
import com.sebastiansokolowski.auctionhunter.entity.Target
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/target")
class TargetController {

    @Autowired
    private lateinit var targetDao: TargetDao

    @GetMapping
    fun getTargets(): List<Target> {
        return targetDao.findAll().toList()
    }

    @PostMapping
    fun createTarget(@RequestBody target: Target): Target {
        return targetDao.save(target)
    }

    @GetMapping("/{id}")
    fun getTarget(@PathVariable("id") id: Long): ResponseEntity<Target> {
        return targetDao.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun updateTarget(@PathVariable("id") id: Long, @RequestBody target: Target): ResponseEntity<Target> {
        return if (targetDao.existsById(id)) {
            target.id = id
            targetDao.save(target)
            ResponseEntity.ok(target)
        } else {
            (ResponseEntity.notFound().build())
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTarget(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return targetDao.findById(id).map {
            targetDao.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}

