package com.le.aestroider.domain

data class NearEarthObject(
    val id: String,
    val name: String,
    val isPotentialyHazerdous: Boolean,
    val closeApproachDate: String
)