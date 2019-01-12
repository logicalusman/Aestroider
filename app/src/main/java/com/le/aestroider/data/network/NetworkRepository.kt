package com.le.aestroider.data.network

import com.le.aestroider.data.base.BaseRepository
import com.le.aestroider.data.network.response.NeoFeedResponse
import com.le.aestroider.domain.NearEarthObjectFeed
import com.le.aestroider.domain.Result
import com.le.aestroider.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

/**
 * Performs data operation over the network. This class is internal to data layer - viewmodel(s) should never access this
 * class.
 *
 * @author Usman
 */
class NetworkRepository @Inject constructor(private val neoApi: NasaNeoApi) : BaseRepository {

    private val TAG = "NetworkRepository"

    fun getNeoFeed(startDate: String, endDate: String): Observable<Result<NearEarthObjectFeed>> {
        val neoFeedSubject = SingleSubject.create<Result<NearEarthObjectFeed>>()
        val disposable = neoApi.getNeoFeed(startDate = startDate, endDate = endDate).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                neoFeedSubject.onSuccess(Result.fomData(parseFeed(it)))
            }, {
                neoFeedSubject.onSuccess(Result.fromError(it))
            })

        return neoFeedSubject.toObservable()

    }

    private fun parseFeed(response: NeoFeedResponse): NearEarthObjectFeed {
        val feedStartDate = Utils.getQueryParameter(response.pageLinks.current, NetworkAdapter.QUERY_PARAM_START_DATE)
        val feedEndDate = Utils.getQueryParameter(response.pageLinks.current, NetworkAdapter.QUERY_PARAM_END_DATE)
        val nextFeedStartDate = Utils.getQueryParameter(response.pageLinks.next, NetworkAdapter.QUERY_PARAM_START_DATE)
        val nextFeedEndDate = Utils.getQueryParameter(response.pageLinks.next, NetworkAdapter.QUERY_PARAM_END_DATE)
        val nearEarthObjectFeed = Mapper.toListOfNearEarthObject(response.neoFeed)
        return NearEarthObjectFeed(feedStartDate, feedEndDate, nextFeedStartDate, nextFeedEndDate, nearEarthObjectFeed)
    }


}