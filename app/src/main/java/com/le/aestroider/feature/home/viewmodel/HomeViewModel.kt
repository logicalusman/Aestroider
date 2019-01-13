package com.le.aestroider.feature.home.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.le.aestroider.R
import com.le.aestroider.data.AestroiderRepository
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.util.Utils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: AestroiderRepository) : ViewModel(), LifecycleObserver {
    sealed class ViewState {
        class UpdateTitle(@StringRes val title: Int) : ViewState()
        class ShowLoading(val show: Boolean) : ViewState()
        class UpdateList(val list : List<NearEarthObject>) : ViewState()
        class LaunchNeoDetailsScreen(val nearEarthObject: NearEarthObject) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    // private vars
    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun setTitle() {
        viewState.value =
                ViewState.UpdateTitle(R.string.near_earth_objects)
    }

    fun onNeoItemSelected(nearEarthObject: NearEarthObject){
        viewState.value = ViewState.LaunchNeoDetailsScreen(nearEarthObject)
    }

    fun getNeoFeed() {
        viewState.value = ViewState.ShowLoading(true)
        disposable = repository.getNeoFeed(Utils.getCurrentDate(), Utils.getDateWeekAfterCurrentDate()).subscribe({

            viewState.value = ViewState.ShowLoading(false)
            if( it.success){
                viewState.value = it.data?.feed?.let { feedIt -> ViewState.UpdateList(feedIt) }
            }
//            if (!it.success) {
//                when (it.errorType) {
//                    is ErrorType.NetworkError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.network_error)
//                    is ErrorType.TimeoutError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.connection_timeout)
//                    else -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.unknown_error)
//                }
//            }
        })
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}