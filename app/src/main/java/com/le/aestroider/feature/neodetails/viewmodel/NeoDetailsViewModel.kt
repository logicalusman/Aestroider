package com.le.aestroider.feature.neodetails.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.domain.NearEarthObjectDetailsItem

class NeoDetailsViewModel : ViewModel() {

    sealed class ViewState {
        class UpdateTitle(@StringRes val title: String) : ViewState()
        class UpdateList(val list: List<NearEarthObjectDetailsItem>) : ViewState()
        class LaunchBrowser(val uri: String) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    // private vars
    private var nearEarthObject: NearEarthObject? = null

    fun init(neo: NearEarthObject?) {
        neo?.let {
            nearEarthObject = it
        }
        populateUi(nearEarthObject)
    }

    fun onOpenUriButtonSelected(){
        nearEarthObject?.let {
            viewState.value = ViewState.LaunchBrowser(it.url)
        }
    }

    private fun populateUi(neo: NearEarthObject?) {
        neo?.let {
            updateTitle(it.name)
            updateList(prepareData(it))
        }
    }

    private fun prepareData(neo: NearEarthObject): List<NearEarthObjectDetailsItem> {
        val list = mutableListOf<NearEarthObjectDetailsItem>()
        var iconRes = R.drawable.ic_earth_black_48dp
        var headerTextRes = R.string.no_hazard
        var subTextRes = R.string.object_is_not_on_collision_course_with_earth
        if (neo.isPotentialyHazerdous) {
            headerTextRes = R.string.hazardous
            subTextRes = R.string.collision_course_with_earth
            iconRes = R.drawable.ic_alert_circle_48dp
        }
        // add hazerdous item first
        val item = NearEarthObjectDetailsItem(iconRes, "", subTextRes, true)
        item.hazardousItemHeaderText = headerTextRes
        list.add(item)
        // add brightness item
        list.add(
            NearEarthObjectDetailsItem(
                R.drawable.ic_brightness_5_black_48dp,
                String.format("%.1f", neo.absoluteBrightness),
                R.string.absolute_brightness,
                false
            )
        )
        // add distance item
        list.add(
            NearEarthObjectDetailsItem(
                R.drawable.ic_arrow_expand_black_48dp,
                "${String.format("%.2f", neo.estimatedDiameterMin)} - ${String.format(
                    "%.2f",
                    neo.estimatedDiameterMax
                )}km",
                R.string.estimated_diameter,
                false
            )
        )

        return list
    }

    private fun updateList(list: List<NearEarthObjectDetailsItem>) {
        viewState.value = ViewState.UpdateList(list)
    }

    private fun updateTitle(title: String) {
        viewState.postValue(ViewState.UpdateTitle(title))
    }

}