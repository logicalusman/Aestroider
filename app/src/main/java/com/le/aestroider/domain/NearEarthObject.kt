package com.le.aestroider.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NearEarthObject(
    val id: String,
    val name: String,
    val isPotentialyHazerdous: Boolean,
    val closeApproachDate: String,
    val url: String,
    val absoluteBrightness: Double,
    val estimatedDiameterMin: Double,
    val estimatedDiameterMax: Double
) : Parcelable