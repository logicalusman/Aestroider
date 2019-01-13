package com.le.aestroider.feature.neodetails.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject


/**
 * A simple [Fragment] subclass.
 *
 */
class NeoDetailsFragment : Fragment() {

    var nearEarthObject: NearEarthObject? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_neo_details, container, false)
    }


    companion object {

        fun newInstance(nearEarthObject: NearEarthObject): NeoDetailsFragment {
            val fragment = NeoDetailsFragment()
            fragment.nearEarthObject = nearEarthObject
            return fragment
        }
    }


}
