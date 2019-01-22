package com.le.aestroider.feature.home.ui


import android.app.Activity
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
import com.le.aestroider.feature.common.ui.ErrorActivity
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
    private val TAG = "HomeFragment"

    init {
        AestroiderApp.dataComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewStates()
        lifecycle.addObserver(viewModel)
        setupViews()
        getNeoFeed(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            getNeoFeed(true)
        }
    }

    private fun subscribeViewStates() {
        viewModel.viewState.observe(activity!!, Observer {
            when (it) {
                is HomeViewModel.ViewState.UpdateTitle -> activity!!.setTitle(it.title)
                is HomeViewModel.ViewState.UpdateList -> updateNeoFeed(it.list)
                is HomeViewModel.ViewState.ClearList -> clearNeoFeed()
                is HomeViewModel.ViewState.ShowLoading -> showLoading(it.show)
                is HomeViewModel.ViewState.LaunchNeoDetailsScreen -> launchNeoDetailsActivity(it.nearEarthObject)
                is HomeViewModel.ViewState.ShowErrorMessage -> showErrorMessage(it.show, it.message)
            }
        })
    }

    private fun setupViews() {
        setupRecyclerView()
        homeAdapter = HomeAdapter(activity!!)
        home_rv.adapter = homeAdapter
        swipe_to_fresh.setOnRefreshListener {
            getNeoFeed(true)
        }
        homeAdapter?.let {
            it.onClickObserver.observe(this, Observer { data ->
                viewModel.onNeoItemSelected(data)
            })

            it.loadNextPageObserver.observe(this, Observer { _ ->
                viewModel.getNextNeoFeed()
            })
        }

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        home_rv.layoutManager = layoutManager
        home_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
    }

    private fun getNeoFeed(forceRefresh: Boolean) {
        viewModel.getNeoFeed(forceRefresh)
    }

    private fun updateNeoFeed(feed: MutableList<NearEarthObject>) {
        homeAdapter?.addAll(feed)
        swipe_to_fresh.isRefreshing = false
    }

    private fun clearNeoFeed() {
        homeAdapter?.clearAll()
    }

    private fun showLoading(show: Boolean) {
        swipe_to_fresh.isRefreshing = show
    }

    private fun showErrorMessage(show: Boolean, @StringRes message: Int) {
        val intent = Intent(activity!!, ErrorActivity::class.java)
        startActivityForResult(intent, 0)
    }

    private fun launchNeoDetailsActivity(nearEarthObject: NearEarthObject) {
        val intent = Intent(activity, NeoDetailsActivity::class.java)
        intent.putExtra(NeoDetailsActivity.EXTRA_NEO_DETAILS, nearEarthObject)
        startActivity(intent)
    }
}
