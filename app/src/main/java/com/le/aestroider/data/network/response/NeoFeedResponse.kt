package com.le.aestroider.data.network.response

import com.google.gson.annotations.SerializedName

/**
 * Note that NEO (Near Earth Object) api returns much detailed info, the only info required by the app is
 * preserved below. More details on https://api.nasa.gov/api.html#NeoWS
 *
 * @author Usman
 */
data class NeoFeedResponse(
    @field:SerializedName("links") val pageLinks: PageLinks,
    @field:SerializedName("element_count") val numNeoObjects: Int,
    @field:SerializedName("near_earth_objects") val neoFeed: Map<String, List<Neo>>
)

data class PageLinks(
    val next: String, val prev: String,
    @field:SerializedName("self") val current: String
)

data class Neo(
    val id: String,
    val name: String,
    @field:SerializedName("is_potentially_hazardous_asteroid") val isPotentiallyHazerdous: Boolean,
    @field:SerializedName("absolute_magnitude_h") val absoluteBrightness: Double,
    @field:SerializedName("estimated_diameter") val estimatedDiameter: EstimatedDiameter

)

data class EstimatedDiameter(val kilometers: Kilometers)

data class Kilometers(
    @field:SerializedName("estimated_diameter_min") val estimatedDiameterMin: Double,
    @field:SerializedName("estimated_diameter_max") val estimatedDiameterMax: Double
)



