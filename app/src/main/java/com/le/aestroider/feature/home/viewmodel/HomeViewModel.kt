package com.le.aestroider.feature.home.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.le.aestroider.R
import com.le.aestroider.data.AestroiderRepository
import com.le.aestroider.domain.ErrorType
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.domain.NearEarthObjectFeed
import com.le.aestroider.util.Utils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: AestroiderRepository) : ViewModel(), LifecycleObserver {

    sealed class ViewState {
        class UpdateTitle(@StringRes val title: Int) : ViewState()
        class ShowLoading(val show: Boolean) : ViewState()
        class UpdateList(val list: MutableList<NearEarthObject>) : ViewState()
        class ClearList():ViewState()
        class LaunchNeoDetailsScreen(val nearEarthObject: NearEarthObject) : ViewState()
        class ShowErrorMessage(val show: Boolean, @StringRes val message: Int) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    // private vars
    private var disposable: Disposable? = null
    // acts as a cache, this will avoid redundant trips to the network api
    private var feedCache: NearEarthObjectFeed? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun setTitle() {
        viewState.value =
                ViewState.UpdateTitle(R.string.near_earth_objects)
    }

    fun onNeoItemSelected(nearEarthObject: NearEarthObject) {
        viewState.value = ViewState.LaunchNeoDetailsScreen(nearEarthObject)
    }

    /**
     * This method must be called to get next feed after the feed is retrieved successfully via getNeoFeed() once.
     */
    fun getNextNeoFeed() {
        feedCache?.let {
            viewState.value = ViewState.ShowLoading(true)
            getFeed(it.nextFeedStartDate, it.nextFeedEndDate,false)
        }
    }

    /**
     * Setting forceRefresh to true will call the api, the cache will be ignored.
     * Call this method very first time to get the feed.
     *
     */
    fun getNeoFeed(forceRefresh: Boolean) {
        if (!forceRefresh && feedCache != null) {
            viewState.value = feedCache?.feed?.let { ViewState.UpdateList(it) }
            return
        }
        getFeed(Utils.getCurrentDate(), Utils.getDateWeekAfterCurrentDate(),true)
    }

    private fun getFeed(startDate: String, endDate: String, forceRefresh: Boolean) {
        // in case error message was displayed before, it will dismiss it
        viewState.value = ViewState.ShowErrorMessage(false, 0)
        // show loading
        viewState.value = ViewState.ShowLoading(true)
        // fetch data
        disposable = repository.getNeoFeed(startDate, endDate).subscribe {
            // fetch call finished here, so dismiss loading show
            viewState.value = ViewState.ShowLoading(false)
            if (it.success) {
                // data received, populate the feed
                // it was a force refresh, so clear existing list
                if( forceRefresh ){
                    viewState.value = ViewState.ClearList()
                }
                viewState.value = it.data?.feed?.let { feedIt ->
                    feedCache = it.data
                    ViewState.UpdateList(feedIt)
                }
            } else {
                // Oops! error, notify about the error
                when (it.errorType) {
                    // different error message can be displayed depending on error type
                    is ErrorType.NetworkError -> viewState.value =
                            ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
                    is ErrorType.TimeoutError -> viewState.value =
                            ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
                    else -> viewState.value = ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
                }
            }
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}