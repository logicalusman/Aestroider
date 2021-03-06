package com.le.aestroider.feature.neodetails.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.le.aestroider.R

class NeoDetailsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NEO_DETAILS = "com.le.aestroider.extra_neo_details"
    }
    // fragments of this activity can simply observe to it for a user click on share menu item
    val shareClickObserver = MutableLiveData<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neo_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fragment: Fragment = NeoDetailsFragment.newInstance(
            intent.getParcelableExtra(
                EXTRA_NEO_DETAILS
            )
        )
        supportFragmentManager.beginTransaction().add(R.id.neo_details_fragment_container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.neo_details_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_share -> {
                shareClickObserver.value = Unit
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
