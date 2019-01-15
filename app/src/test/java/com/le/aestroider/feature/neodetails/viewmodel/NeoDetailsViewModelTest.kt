package com.le.aestroider.feature.neodetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.domain.NearEarthObjectDetailsItem
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito

class NeoDetailsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    val neoDetailsViewModel = NeoDetailsViewModel()
    val mockObserver: Observer<NeoDetailsViewModel.ViewState> = mock()
    val argumentCaptor = argumentCaptor<NeoDetailsViewModel.ViewState>()
    val list = mutableListOf<NearEarthObjectDetailsItem>()
    val neoItem = NearEarthObject(
        "id-1", "Alpha aes", false, "2018-05-07",
        "http://url.com", 16.7, 1.21, 2.89
    )

    @Before
    fun setUp() {
        neoDetailsViewModel.viewState.observeForever(mockObserver)
        val item = NearEarthObjectDetailsItem(
            R.drawable.ic_earth_black_48dp,
            "", R.string.object_is_not_on_collision_course_with_earth,
            true
        )
        item.hazardousItemHeaderText = R.string.no_hazard
        list.add(item)
        // add brightness item
        list.add(
            NearEarthObjectDetailsItem(
                R.drawable.ic_brightness_5_black_48dp,
                "16.7",
                R.string.absolute_brightness,
                false
            )
        )
        // add distance item
        list.add(
            NearEarthObjectDetailsItem(
                R.drawable.ic_arrow_expand_black_48dp,
                "1.21 - 2.89km",
                R.string.estimated_diameter,
                false
            )
        )
    }

    @Test
    fun init() {
        neoDetailsViewModel.init(neoItem)
        Mockito.verify(mockObserver, Mockito.times(2)).onChanged(argumentCaptor.capture())
        val listOfStates = argumentCaptor.allValues
        // verify states first
        Assert.assertTrue(listOfStates[0] is NeoDetailsViewModel.ViewState.UpdateTitle)
        Assert.assertTrue(listOfStates[1] is NeoDetailsViewModel.ViewState.UpdateList)
        // verify screen title
        Assert.assertEquals("Alpha aes", (listOfStates[0] as NeoDetailsViewModel.ViewState.UpdateTitle).title)
        // verify list
        val list = (listOfStates[1] as NeoDetailsViewModel.ViewState.UpdateList).list
        Assert.assertEquals(3, list.size)
        // Add more tests for checking every list item

    }

    @Test
    fun onOpenUriAction() {
        neoDetailsViewModel.init(neoItem)
        neoDetailsViewModel.onOpenUriAction()
        Mockito.verify(mockObserver)
            .onChanged(neoDetailsViewModel.viewState.value as NeoDetailsViewModel.ViewState.LaunchBrowser)
        //verify uri
        Assert.assertEquals(
            neoItem.url,
            (neoDetailsViewModel.viewState.value as NeoDetailsViewModel.ViewState.LaunchBrowser).uri
        )
    }

    @Test
    fun onShareUriAction() {
        neoDetailsViewModel.init(neoItem)
        neoDetailsViewModel.onShareUriAction()
        Mockito.verify(mockObserver)
            .onChanged(neoDetailsViewModel.viewState.value as NeoDetailsViewModel.ViewState.ShareData)
        //verify uri
        Assert.assertEquals(
            neoItem.url,
            (neoDetailsViewModel.viewState.value as NeoDetailsViewModel.ViewState.ShareData).data
        )
    }
}