package com.le.aestroider.feature.common.ui


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.le.aestroider.R
import kotlinx.android.synthetic.main.fragment_error.*


/**
 * Displays error message and a Retry button. Tapping on retry goes back to the calling
 * activity/fragment via startActivityForResult() pattern.
 *
 * @author Usman
 *
 */
class ErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.setTitle(R.string.near_earth_objects)
        retry_btn.setOnClickListener {
            activity!!.setResult(Activity.RESULT_OK)
            activity!!.finish()
        }
    }


}
