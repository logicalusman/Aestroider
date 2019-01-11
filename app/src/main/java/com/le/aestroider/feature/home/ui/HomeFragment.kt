package com.le.aestroider.feature.home.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.le.aestroider.R
import com.le.aestroider.app.AestroiderApp
import com.le.aestroider.feature.home.viewmodel.HomeViewModel
import javax.inject.Inject

/**
 * Home screen. Shows the list of aestroid objects retrieved from the Nasa api.
 *
 * @author Usman
 *
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    init {
        AestroiderApp.dataComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(HomeViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewStates()
        lifecycle.addObserver(viewModel)
        setupViews()
    }

    private fun subscribeViewStates() {
        viewModel.viewState.observe(activity!!, Observer {
            when (it) {
                is HomeViewModel.ViewState.UpdateTitle -> activity!!.setTitle(it.title)
            }
        })
    }

    private fun setupViews() {
        viewModel.getNeoFeed()

    }
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}
