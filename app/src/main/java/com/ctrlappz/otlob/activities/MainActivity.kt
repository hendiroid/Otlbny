package com.ctrlappz.otlob.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.fragments.HomeFragment
import com.ctrlappz.otlob.fragments.MapFragment
import com.ctrlappz.otlob.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFrag(HomeFragment())
            }
            R.id.navigation_map -> {
                openFrag(MapFragment())
            }
            R.id.navigation_profile -> {
                openFrag(ProfileFragment())
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFrag(HomeFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
