package com.le.aestroider.feature.home.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
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
import com.le.aestroider.feature.neodetails.ui.NeoDetailsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Home screen. Shows the list of aestroid objects retrieved from the Neo api.
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

    private fun subscribeViewStates() {
        viewModel.viewState.observe(activity!!, Observer {
            when (it) {
                is HomeViewModel.ViewState.UpdateTitle -> activity!!.setTitle(it.title)
                is HomeViewModel.ViewState.UpdateList -> updateNeoFeed(it.list)
                is HomeViewModel.ViewState.ShowLoading -> showLoading(it.show)
                is HomeViewModel.ViewState.LaunchNeoDetailsScreen -> launchNeoDetailsActivity(it.nearEarthObject)
                is HomeViewModel.ViewState.ShowErrorMessage -> showErrorMessage(it.show, it.message)
            }
        })
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
        homeAdapter?.onClickObserver?.observe(this, Observer {
            viewModel.onNeoItemSelected(it)
        })
        retry_btn.setOnClickListener {
            viewModel.getNeoFeed()
        }
    }

    private fun getNeoFeed() {
        viewModel.getNeoFeed()
    }

    private fun updateNeoFeed(feed: MutableList<NearEarthObject>) {
        homeAdapter?.listItems = feed
        swipe_to_fresh.isRefreshing = false
    }

    private fun showLoading(show: Boolean) {
        swipe_to_fresh.isRefreshing = show
    }

    private fun showErrorMessage(show: Boolean, @StringRes message: Int) {
        if (show) {
            swipe_to_fresh.visibility = View.GONE
            error_container.visibility = View.VISIBLE
            retry_btn.visibility = View.VISIBLE
            error_msg_tv.setText(message)
        } else {
            hideErrorMessage()
        }
    }

    private fun hideErrorMessage() {
        swipe_to_fresh.visibility = View.VISIBLE
        error_container.visibility = View.GONE
        retry_btn.visibility = View.GONE

    }

    private fun launchNeoDetailsActivity(nearEarthObject: NearEarthObject) {
        val intent = Intent(activity, NeoDetailsActivity::class.java)
        intent.putExtra(NeoDetailsActivity.EXTRA_NEO_DETAILS, nearEarthObject)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}
