package com.le.aestroider.data.network

import android.util.Log
import com.le.aestroider.data.base.BaseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Performs data operation over the network. This class is private to data layer - viewmodel(s) should never access this
 * class.
 *
 * @author Usman
 */
class NetworkRepository @Inject constructor(private val neoApi: NasaNeoApi) : BaseRepository {

    private val TAG = "NetworkRepository"

    fun getNeoFeed() {

        neoApi.getMyPriorities(startDate = "2018-01-11", endDate = "2018-01-18").subscribeOn(Schedulers.io()).map {
            Log.d(TAG, "Number of objects: ${it.numNeoObjects}")
        }
            .observeOn(AndroidSchedulers.mainThread()).subscribe()


    }
}