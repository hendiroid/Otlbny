package com.ctrlappz.otlob.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        map.onCreate(savedInstanceState)
        map.onResume()
        map.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true
        }

        doneBT.setOnClickListener {
            val intent = Intent()
            intent.putExtra("lat", lat)
            intent.putExtra("lng", lng)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { latLng ->
            lat = latLng.latitude
            lng = latLng.longitude

            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)

            mMap.clear()

            mMap.addMarker(markerOptions)

            mMap.isMyLocationEnabled = true
        }
    }
}
