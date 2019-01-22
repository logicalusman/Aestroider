package com.le.aestroider.data

import com.le.aestroider.data.network.NetworkRepository
import com.le.aestroider.domain.NearEarthObjectFeed
import io.reactivex.Observable
import javax.inject.Inject

/**
 * <p>
 * Facade for data operations, a.k.a repository pattern. This class is a central point for
 * viewmodels to perform data operations without knowing how the underlying operation is performed.
 * </p>
 *
 * @author Usman
 */
class AestroiderRepository @Inject constructor(private val networkRepository: NetworkRepository) {

    fun getNeoFeed(startDate: String, endDate: String): Observable<NearEarthObjectFeed> {
        return networkRepository.getNeoFeed(startDate, endDate)
    }

    /**
     * Gets the number of maximum days the feed can retrieved
     */
    fun getMaxDaysFeedLimit(): Int {
        return networkRepository.maxDaysFeedLimit
    }
}