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
import com.le.aestroider.domain.Result
import com.le.aestroider.util.Utils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: AestroiderRepository) : ViewModel(), LifecycleObserver {

    sealed class ViewState {
        class UpdateTitle(@StringRes val title: Int) : ViewState()
        class ShowLoading(val show: Boolean) : ViewState()
        class UpdateList(val list: MutableList<NearEarthObject>) : ViewState()
        class ClearList() : ViewState()
        class LaunchNeoDetailsScreen(val nearEarthObject: NearEarthObject) : ViewState()
        class ShowErrorMessage(val show: Boolean, @StringRes val message: Int) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    // private vars
    private data class FeedCache(
        var feedListCache: MutableList<NearEarthObject>,
        var nextStartDateCache: String,
        var nextEndDateCache: String
    )

    private var disposable: Disposable? = null
    // Acts as in-memory cache, this will avoid redundant trips to the network api esp. when rotating device
    private var feedCache: FeedCache? = null

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
            getFeed(it.nextStartDateCache, it.nextEndDateCache, false)
        }
    }

    /**
     * Setting forceRefresh to true will force calling the api, ignoring the cache, provided that the cache is not empty.
     * Call this method very first time to get the feed or when the view rotates otherwise call getNextNeoFeed()
     *
     */
    fun getNeoFeed(forceRefresh: Boolean) {
        if (!forceRefresh && feedCache != null) {
            viewState.value = feedCache?.feedListCache?.let { ViewState.UpdateList(it) }
            return
        }
        val currentDate = Utils.getCurrentDate()
        getFeed(currentDate, Utils.addDaysToDate(currentDate, repository.getMaxDaysFeedLimit() - 1), forceRefresh)
    }

    private fun getFeed(startDate: String, endDate: String, forceRefresh: Boolean) {
        // show loading
        viewState.value = ViewState.ShowLoading(true)
        // fetch data
        disposable = repository.getNeoFeed(startDate, endDate).subscribe {
            // fetch call finished here, so dismiss loading show
            viewState.value = ViewState.ShowLoading(false)
            if (it.success) {
                // data received successfully, populate the feed
                handleSuccess(forceRefresh, it)
            } else {
                // Oops! error, notify about the error
                handleError(it)
            }
        }
    }

    private fun handleSuccess(forceRefresh: Boolean, result: Result<NearEarthObjectFeed>) {
        updateCache(forceRefresh, result)
        viewState.value = result.data?.feed?.let { feedIt ->
            ViewState.UpdateList(feedIt)
        }
    }

    private fun updateCache(forceRefresh: Boolean, result: Result<NearEarthObjectFeed>) {
        result.data?.let {
            if (forceRefresh) {
                // result was a force refresh, so clear existing list
                viewState.value = ViewState.ClearList()
                // replace existing cache with new data
                feedCache?.feedListCache = it.feed
            } else {
                if (feedCache == null) {
                    // first time, init cache first
                    feedCache = FeedCache(it.feed, it.nextFeedStartDate, it.nextFeedEndDate)
                } else {
                    feedCache?.feedListCache?.addAll(it.feed)
                }
            }
            updateDateInCache(it)
        }
    }

    private fun updateDateInCache(it: NearEarthObjectFeed) {
        // Increment next feed start date to 1 day; this is because the neo api sets the next feed start date value to
        // the end date of the previous call. This will avoid getting data of the same date twice
        feedCache?.nextStartDateCache = Utils.addDaysToDate(it.nextFeedStartDate, 1)
        feedCache?.nextEndDateCache = Utils.addDaysToDate(it.nextFeedStartDate, repository.getMaxDaysFeedLimit())
    }

    private fun handleError(result: Result<NearEarthObjectFeed>) {
        when (result.errorType) {
            // different error message can be displayed depending on error type
            is ErrorType.NetworkError -> viewState.value =
                    ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
            is ErrorType.TimeoutError -> viewState.value =
                    ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
            else -> viewState.value = ViewState.ShowErrorMessage(true, R.string.internet_error_msg)
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        feedCache?.feedListCache?.clear()
        super.onCleared()
    }
}