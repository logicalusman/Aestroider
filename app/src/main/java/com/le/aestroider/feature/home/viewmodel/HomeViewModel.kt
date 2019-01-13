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
        class UpdateList(val list: List<NearEarthObject>) : ViewState()
        class LaunchNeoDetailsScreen(val nearEarthObject: NearEarthObject) : ViewState()
        class ShowErrorMessage(val show: Boolean, @StringRes val message: Int) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    // private vars
    private var disposable: Disposable? = null
    // acts as a cache, this will avoid redundant trips to the network api
    private var feedCache : NearEarthObjectFeed? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun setTitle() {
        viewState.value =
                ViewState.UpdateTitle(R.string.near_earth_objects)
    }

    fun onNeoItemSelected(nearEarthObject: NearEarthObject) {
        viewState.value = ViewState.LaunchNeoDetailsScreen(nearEarthObject)
    }

    fun getNeoFeed() {
        // in case error message was displayed before, it will dismiss it
        viewState.value = ViewState.ShowErrorMessage(false, 0)
        // show loading
        viewState.value = ViewState.ShowLoading(true)
        // fetch data
        disposable = repository.getNeoFeed(Utils.getCurrentDate(), Utils.getDateWeekAfterCurrentDate()).subscribe {
            // fetch call finished here, so dismiss loading show
            viewState.value = ViewState.ShowLoading(false)
            if (it.success) {
                // data received, populate the feed
                viewState.value = it.data?.feed?.let { feedIt -> ViewState.UpdateList(feedIt) }
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