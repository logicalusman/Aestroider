package com.le.aestroider.feature.home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.le.aestroider.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val fragment: Fragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.home_fragment_container, fragment).commit()
    }
}
