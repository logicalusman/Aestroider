package com.le.aestroider.domain

data class NearEarthObjectFeed(
    val feedStartDate: String,
    val feedEndDate: String,
    val nextFeedStartDate: String,
    val nextFeedEndDate: String,
    val feed: List<NearEarthObject>
)