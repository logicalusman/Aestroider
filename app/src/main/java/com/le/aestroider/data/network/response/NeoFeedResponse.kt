package com.le.aestroider.data.network.response

import com.google.gson.annotations.SerializedName

data class NeoFeedResponse(
    @field:SerializedName("links") val pageLinks: HashMap<String, String>,
    @field:SerializedName("element_count") val numNeoObjects: Int
)


