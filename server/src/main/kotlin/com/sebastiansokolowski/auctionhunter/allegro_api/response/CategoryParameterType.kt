package com.sebastiansokolowski.auctionhunter.allegro_api.response

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