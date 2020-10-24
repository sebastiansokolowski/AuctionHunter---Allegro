package com.sebastiansokolowski.auctionhunter.controller

import com.sebastiansokolowski.auctionhunter.allegro_api.AllegroClient
import com.sebastiansokolowski.auctionhunter.allegro_api.response.Categories
import com.sebastiansokolowski.auctionhunter.allegro_api.response.CategoryParameters
import com.sebastiansokolowski.auctionhunter.allegro_api.response.Listing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/allegro/")
class AllegroController {

    @Autowired
    private lateinit var allegroClient: AllegroClient

    @GetMapping("categories", "categories/{parentId}")
    fun getCategories(@PathVariable("parentId", required = false) parentId: String?): Categories? {
        return allegroClient.getAllegroApi().getCategories(parentId).execute().body()
    }


    @GetMapping("categories/{categoryId}/parameters")
    fun getCategoryParameters(@PathVariable("categoryId") categoryId: String): CategoryParameters? {
        return allegroClient.getAllegroApi().getCategoryParameters(categoryId).execute().body()
    }

    @GetMapping("offers")
    fun getOffers(
            @RequestParam("category.id") categoryId: String? = null,
            @RequestParam("phrase") phrase: String? = null,
            @RequestParam("fallback") fallback: Boolean = false,
            @RequestParam("sort") sort: String = "+price",
            @RequestParam("include") excludeObjects: String = "-all",
            @RequestParam("include") includeObjects: String = "items",
            @RequestParam parameters: Map<String, String>
    ): Listing? {
        return allegroClient.getAllegroApi()
                .getOffers(categoryId, phrase, fallback, sort, excludeObjects, includeObjects, parameters)
                .execute().body()
    }
}

