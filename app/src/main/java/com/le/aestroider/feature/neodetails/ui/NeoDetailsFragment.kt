package com.le.aestroider.feature.neodetails.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.domain.NearEarthObjectDetailsItem
import com.le.aestroider.feature.neodetails.adapter.NeoDetailsAdapter
import com.le.aestroider.feature.neodetails.viewmodel.NeoDetailsViewModel
import kotlinx.android.synthetic.main.fragment_neo_details.*


/**
 * Show Neo (Near Earth Object) details
 *
 */
class NeoDetailsFragment : Fragment() {

    var nearEarthObject: NearEarthObject? = null

    // private vars
    private lateinit var viewModel: NeoDetailsViewModel
    private var neoDetailsAdapter: NeoDetailsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_neo_details, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(NeoDetailsViewModel::class.java)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        subscribeViewStates()
        viewModel.init(nearEarthObject)
    }

    private fun subscribeViewStates() {
        viewModel.viewState.observe(activity!!, Observer {
            when (it) {
                is NeoDetailsViewModel.ViewState.UpdateTitle -> activity!!.title = it.title
                is NeoDetailsViewModel.ViewState.UpdateList -> updateNeoFeedDetails(it.list)
                is NeoDetailsViewModel.ViewState.LaunchBrowser -> launchBrowser(it.uri)
            }
        })
    }

    private fun updateNeoFeedDetails(list: List<NearEarthObjectDetailsItem>) {
        neoDetailsAdapter?.listItems = list
    }

    private fun launchBrowser(uri: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(uri)
        startActivity(i)
    }

    private fun setupViews() {
        val layoutManager = LinearLayoutManager(activity)
        neo_details_rv.layoutManager = layoutManager
        neo_details_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        neoDetailsAdapter = NeoDetailsAdapter(activity!!)
        neo_details_rv.adapter = neoDetailsAdapter
        url_btn.setOnClickListener {
            viewModel.onOpenUriButtonSelected()
        }
    }

    companion object {

        fun newInstance(nearEarthObject: NearEarthObject): NeoDetailsFragment {
            val fragment = NeoDetailsFragment()
            fragment.nearEarthObject = nearEarthObject
            return fragment
        }
    }


}
