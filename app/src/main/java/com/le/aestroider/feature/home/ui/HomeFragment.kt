package com.le.aestroider.feature.home.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.le.aestroider.R
import com.le.aestroider.app.AestroiderApp
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.feature.home.adapter.HomeAdapter
import com.le.aestroider.feature.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Home screen. Shows the list of aestroid objects retrieved from the Nasa Neo api.
 *
 * @author Usman
 *
 */
class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    // private vars
    private lateinit var viewModel: HomeViewModel
    private var homeAdapter: HomeAdapter? = null

    init {
        AestroiderApp.dataComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(HomeViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewStates()
        lifecycle.addObserver(viewModel)
        setupViews()
        getNeoFeed()
    }

    private fun getNeoFeed() {
        viewModel.getNeoFeed()
    }

    private fun subscribeViewStates() {
        viewModel.viewState.observe(activity!!, Observer {
            when (it) {
                is HomeViewModel.ViewState.UpdateTitle -> activity!!.setTitle(it.title)
                is HomeViewModel.ViewState.UpdateList -> updateNeoFeed(it.list)
                is HomeViewModel.ViewState.ShowLoading -> showLoading(it.show)
            }
        })
    }

    private fun updateNeoFeed(feed: List<NearEarthObject>) {
        homeAdapter?.listItems = feed
        swipe_to_fresh.isRefreshing = false
    }

    private fun showLoading(show: Boolean) {
        swipe_to_fresh.isRefreshing = show
    }

    private fun setupViews() {
        val layoutManager = LinearLayoutManager(activity)
        home_rv.layoutManager = layoutManager
        home_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        homeAdapter = HomeAdapter(activity!!)
        home_rv.adapter = homeAdapter
        swipe_to_fresh.setOnRefreshListener {
            getNeoFeed()
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}
