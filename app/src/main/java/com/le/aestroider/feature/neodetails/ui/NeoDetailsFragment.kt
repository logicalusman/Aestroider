package com.le.aestroider.feature.neodetails.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.feature.neodetails.viewmodel.NeoDetailsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class NeoDetailsFragment : Fragment() {

    var nearEarthObject: NearEarthObject? = null

    // private vars
    private lateinit var viewModel: NeoDetailsViewModel

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
    }


    companion object {

        fun newInstance(nearEarthObject: NearEarthObject): NeoDetailsFragment {
            val fragment = NeoDetailsFragment()
            fragment.nearEarthObject = nearEarthObject
            return fragment
        }
    }


}
