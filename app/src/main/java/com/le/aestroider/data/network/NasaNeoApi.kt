package com.le.aestroider.data.network

import com.le.aestroider.data.network.response.NeoFeedResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaNeoApi {

    @GET(NetworkAdapter.GET_NEO_FEED)
    fun getMyPriorities(
        @Query(value = "start_date") startDate: String,
        @Query(value = "end_date") endDate: String,
        @Query(value = "api_key") apiKey: String = NetworkAdapter.API_AUTH_KEY
    ):
            Observable<NeoFeedResponse>
}