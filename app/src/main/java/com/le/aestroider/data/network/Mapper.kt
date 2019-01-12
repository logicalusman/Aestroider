package com.le.aestroider.data.network

import com.le.aestroider.data.network.response.Neo
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.util.Utils

/**
 * Converts layer's internal objects into domain (POJO) objects, so they can be passed
 * independently between layers.
 *
 * @author Usman
 */
object Mapper {

    fun toListOfNearEarthObject(neoFeed: Map<String, List<Neo>>): List<NearEarthObject> {
        val listOfNearEarthObject = mutableListOf<NearEarthObject>()
        neoFeed.forEach {
            val closeApproachDate = Utils.toDateFormatIn_dd_MM_yyyy(it.key)
            it.value.forEach { neo ->
                val nearEarthObject = NearEarthObject(neo.id, neo.name, neo.isPotentiallyHazerdous, closeApproachDate)
                listOfNearEarthObject.add(nearEarthObject)
            }
        }
        return listOfNearEarthObject
    }
}