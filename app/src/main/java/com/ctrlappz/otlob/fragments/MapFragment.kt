package com.ctrlappz.otlob.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.WorkersApi
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapFragment : Fragment() {

    private var longitude: String? = null
    private var latitude: String? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        rootView.map.onCreate(savedInstanceState)
        rootView.map.onResume()
        rootView.map.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true


            val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 10)

            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                if (location != null) {
                    longitude = java.lang.Double.toString(location.longitude)
                    latitude = java.lang.Double.toString(location.latitude)

                    val latLng = LatLng((latitude!!.toDouble()), (longitude!!.toDouble()))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))

                }
            }

            getWorkers(googleMap)
        }
        return rootView
    }

    private fun getWorkers(googleMap: GoogleMap) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val workersApi = retrofit.create<WorkersApi>(WorkersApi::class.java)
        val connection = workersApi.getWorkers(Links.API + Links.WORKERS)
        connection.enqueue(object : Callback<Array<WorkerModel>> {
            override fun onResponse(call: Call<Array<WorkerModel>>?, response: Response<Array<WorkerModel>>?) {
                if (response!!.isSuccessful) {
                    val workerModel = response.body()
                    for (i in 0 until workerModel!!.size) {
                        val worker = workerModel[i]

                        val long = worker.longitude
                        val lat = worker.latitude

                        val latLng = LatLng((lat!!.toDouble()), (long!!.toDouble()))

                        googleMap.addMarker(MarkerOptions()
                                .position(latLng)
                        ).tag = worker
                    }
                } else {
                    Helper.getErrorMessage(context, response)
                }
            }

            override fun onFailure(call: Call<Array<WorkerModel>>?, t: Throwable?) {

            }

        })

    }

}
