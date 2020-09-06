package com.sebastian.sokolowski.auctionhunter.rest.response

import com.google.gson.internal.LinkedTreeMap

data class CategoryParameter @JvmOverloads constructor(
        val id: String,
        val name: String,
        val type: CategoryParameterType,
        val required: Boolean,
        val requiredForProduct: Boolean,
        val unit: String?,
        val restrictions: LinkedTreeMap<String, Any>,
        val dictionary: List<CategoryParameterDictionary>?
)