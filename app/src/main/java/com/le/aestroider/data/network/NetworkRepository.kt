package com.le.aestroider.data.network

import android.util.Log
import com.le.aestroider.data.base.BaseRepository
import com.le.aestroider.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Performs data operation over the network. This class is internal to data layer - viewmodel(s) should never access this
 * class.
 *
 * @author Usman
 */
class NetworkRepository @Inject constructor(private val neoApi: NasaNeoApi) : BaseRepository {

    private val TAG = "NetworkRepository"

    fun getNeoFeed() {

        neoApi.getMyPriorities(startDate = "2018-01-12", endDate = "2018-01-19").subscribeOn(Schedulers.io()).map {

            val sortedMap = it.neoFeed.toSortedMap(Utils.dateComparator())

            Log.d(TAG, "Number of objects: ${it.numNeoObjects}")
            Log.d(TAG, it.pageLinks.next)
            Log.d(TAG, "Neo feed size: ${sortedMap.keys}")

        }
            .observeOn(AndroidSchedulers.mainThread()).subscribe()


    }
}