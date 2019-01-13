package com.le.aestroider.feature.neodetails.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.le.aestroider.R

class NeoDetailsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NEO_DETAILS = "com.le.aestroider.extra_neo_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neo_details)
        val fragment: Fragment = NeoDetailsFragment.newInstance(
            intent.getParcelableExtra(
                EXTRA_NEO_DETAILS
            )
        )
        supportFragmentManager.beginTransaction().add(R.id.neo_details_fragment_container, fragment).commit()
    }
}
