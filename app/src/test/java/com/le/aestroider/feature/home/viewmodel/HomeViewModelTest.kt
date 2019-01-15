package com.le.aestroider.feature.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.le.aestroider.R
import com.le.aestroider.data.AestroiderRepository
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.domain.NearEarthObjectFeed
import com.le.aestroider.domain.Result
import com.le.aestroider.util.Utils
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times

class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    val listOfNeo = mutableListOf<NearEarthObject>()
    var neoFeed: NearEarthObjectFeed? = null
    var result: Result<NearEarthObjectFeed>? = null
    var resultObservable: Observable<Result<NearEarthObjectFeed>>? = null
    lateinit var homeViewModel: HomeViewModel
    val aestroiderRepositoryMock: AestroiderRepository = mock()
    val mockObserver: Observer<HomeViewModel.ViewState> = mock()
    val argumentCaptor = argumentCaptor<HomeViewModel.ViewState>()


    val neoItem = NearEarthObject(
        "id-1", "Alpha aes", true, "2018-05-07",
        "http://url.com", 12.2, 1.21, 2.89
    )

    @Before
    fun setUp() {
        for (i in 1..10)
            listOfNeo.add(
                NearEarthObject(
                    "id-$i", "Alpha aes$i", true, "2018-05-07",
                    "http://url.com", 12.2, 1.21, 2.89
                )
            )
        neoFeed = NearEarthObjectFeed(
            Utils.getCurrentDate(), Utils.addDaysToDate(Utils.getCurrentDate(), 6),
            Utils.addDaysToDate(Utils.getCurrentDate(), 7), Utils.addDaysToDate(
                Utils.getCurrentDate(), 13
            ), listOfNeo
        )
        neoFeed?.let {
            result = Result.fomData(it)
        }
        resultObservable = Observable.just(result)
        homeViewModel = HomeViewModel(aestroiderRepositoryMock)
        homeViewModel.viewState.observeForever(mockObserver)

        Mockito.doReturn(resultObservable).`when`(aestroiderRepositoryMock).getNeoFeed(
            Utils.getCurrentDate(),
            Utils.addDaysToDate(Utils.getCurrentDate(), 6)
        )

        Mockito.`when`(
            aestroiderRepositoryMock.getMaxDaysFeedLimit()
        ).thenReturn(7)
    }

    @Test
    fun setTitle() {
        // Mock LifecycleOwner. Note that LifecycleOwner in real world is implemented by the view i.e. Activity/Fragment
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.addObserver(homeViewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        // verify if livedata observer has got the right state
        Mockito.verify(mockObserver)
            .onChanged(homeViewModel.viewState.value as HomeViewModel.ViewState.UpdateTitle)
        // verify if UpdateScreenTitle state has the right screen title
        Assert.assertEquals(
            R.string.near_earth_objects, (homeViewModel.viewState.value as HomeViewModel.ViewState.UpdateTitle).title
        )
    }

    @Test
    fun onNeoItemSelected() {
        homeViewModel.onNeoItemSelected(neoItem)
        Mockito.verify(mockObserver)
            .onChanged(homeViewModel.viewState.value as HomeViewModel.ViewState.LaunchNeoDetailsScreen)
        Assert.assertEquals(
            neoItem,
            (homeViewModel.viewState.value as HomeViewModel.ViewState.LaunchNeoDetailsScreen).nearEarthObject
        )
    }


    @Test
    fun getNeoFeed() {
        homeViewModel.getNeoFeed(false)
        // the calls changes ui state 4 times
        Mockito.verify(mockObserver, times(3)).onChanged(argumentCaptor.capture())
        val listOfStates = argumentCaptor.allValues
        Assert.assertTrue(listOfStates[0] is HomeViewModel.ViewState.ShowLoading)
        Assert.assertTrue(listOfStates[1] is HomeViewModel.ViewState.ShowLoading)
        Assert.assertTrue(listOfStates[2] is HomeViewModel.ViewState.UpdateList)

        // check the list
        val list = (listOfStates[2] as HomeViewModel.ViewState.UpdateList).list
        Assert.assertEquals(listOfNeo.size, list.size)
        // nice to test some items
        Assert.assertEquals("Alpha aes4", list.get(3).name)
        Assert.assertEquals("http://url.com", list.get(7).url)
    }

    @Test
    fun getNeoFeed_when_data_is_received_from_cache() {
        // Call get feed twice. 2nd call should not go to repository, it will use cache
        homeViewModel.getNeoFeed(false)
        homeViewModel.getNeoFeed(false)
        verify(aestroiderRepositoryMock, times(1)).getNeoFeed(
            Utils.getCurrentDate(),
            Utils.addDaysToDate(Utils.getCurrentDate(), 6)
        )
    }
}