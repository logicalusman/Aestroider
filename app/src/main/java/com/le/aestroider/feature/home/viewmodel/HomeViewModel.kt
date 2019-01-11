package com.le.aestroider.feature.home.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.le.aestroider.R

class HomeViewModel : ViewModel() , LifecycleObserver {
    sealed class ViewState {
        class UpdateTitle(@StringRes val title: Int) : ViewState()

    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun setTitle() {
        viewState.value =
                ViewState.UpdateTitle(R.string.near_earth_objects)
    }
}