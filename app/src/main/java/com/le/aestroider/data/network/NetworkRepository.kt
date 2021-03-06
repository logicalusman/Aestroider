package com.le.aestroider.data.network

import com.le.aestroider.data.base.BaseRepository
import com.le.aestroider.data.network.response.NeoFeedResponse
import com.le.aestroider.domain.NearEarthObjectFeed
import com.le.aestroider.util.Utils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Performs data operations over the network. This class is internal to data layer - viewmodel(s) should never access this
 * class.
 *
 * @author Usman
 */
class NetworkRepository @Inject constructor(private val neoApi: NasaNeoApi) : BaseRepository {

    private val TAG = "NetworkRepository"
    // The api only allows 7 days of feed in a single call
    val maxDaysFeedLimit = 7

    override fun getNeoFeed(startDate: String, endDate: String): Observable<NearEarthObjectFeed> {

        return neoApi.getNeoFeed(startDate, endDate).map {
            return@map parseFeed(it)
        }

    }

    private fun parseFeed(response: NeoFeedResponse): NearEarthObjectFeed {
        val feedStartDate = Utils.getQueryParameter(response.pageLinks.current, NetworkAdapter.QUERY_PARAM_START_DATE)
        val feedEndDate = Utils.getQueryParameter(response.pageLinks.current, NetworkAdapter.QUERY_PARAM_END_DATE)
        val nextFeedStartDate = Utils.getQueryParameter(response.pageLinks.next, NetworkAdapter.QUERY_PARAM_START_DATE)
        val nextFeedEndDate = Utils.getQueryParameter(response.pageLinks.next, NetworkAdapter.QUERY_PARAM_END_DATE)
        val nearEarthObjectFeed = Mapper.toListOfNearEarthObject(response.neoFeed.toSortedMap(Utils.dateComparator()))
        return NearEarthObjectFeed(feedStartDate, feedEndDate, nextFeedStartDate, nextFeedEndDate, nearEarthObjectFeed)
    }


}