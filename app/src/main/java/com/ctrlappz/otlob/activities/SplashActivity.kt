package com.ctrlappz.otlob.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.item_splash.view.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val apiToken = ProfileInfo(this@SplashActivity).getInformation("apiToken")
        if (apiToken != null) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 10)

            } else if (ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 10)
            }
        }

        viewPager.adapter = SectionsPageAdapter(supportFragmentManager)
        tabDots.setupWithViewPager(viewPager)

        if (viewPager.currentItem == 3) {
            loginBT.visibility = View.VISIBLE
        } else {
            loginBT.visibility = View.GONE

        }

        nextIV.setOnClickListener {
            viewPager.currentItem++
        }
        loginBT.setOnClickListener {
            startActivity(Intent(this@SplashActivity, StartActivity::class.java))
            finish()
        }

        skipTV.setOnClickListener {
            startActivity(Intent(this@SplashActivity, StartActivity::class.java))
            finish()
        }

    }

    inner class SectionsPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 3
        }
    }

    class PlaceholderFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.item_splash, container, false)

            val fragmentIndex = arguments!!.getInt(ARG_SECTION_NUMBER)

            when (fragmentIndex) {
                1 -> {
                    rootView.pagerImages.setImageResource(R.drawable.splash1)
                }
                2 -> {
                    rootView.pagerImages.setImageResource(R.drawable.splash2)
                }
                3 -> {
                    rootView.pagerImages.setImageResource(R.drawable.splash3)
                }
            }

            return rootView
        }

        companion object {
            private const val ARG_SECTION_NUMBER = "section_number"
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

    }
}
