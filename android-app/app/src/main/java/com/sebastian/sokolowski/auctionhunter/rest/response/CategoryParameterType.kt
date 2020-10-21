package com.sebastian.sokolowski.auctionhunter.rest.response

import com.google.gson.annotations.SerializedName

enum class CategoryParameterType {
    @SerializedName("integer")
    INTEGER,

    @SerializedName("float")
    FLOAT,

    @SerializedName("string")
    STRING,

    @SerializedName("dictionary")
    DICTIONARY
}