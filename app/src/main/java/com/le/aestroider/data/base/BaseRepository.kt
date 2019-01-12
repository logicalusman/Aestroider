package com.le.aestroider.data.base

import com.le.aestroider.domain.NearEarthObjectFeed
import com.le.aestroider.domain.Result
import io.reactivex.Observable

interface BaseRepository {
    fun getNeoFeed(startDate: String, endDate: String): Observable<Result<NearEarthObjectFeed>>
}