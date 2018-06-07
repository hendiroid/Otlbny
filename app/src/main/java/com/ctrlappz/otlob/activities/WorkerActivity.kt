package com.ctrlappz.otlob.activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.WorkersApi
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_worker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class WorkerActivity : AppCompatActivity() {

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)
        id = intent.extras!!.getString("id")
        getWorker(id)
    }

    private fun getWorker(id: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val workerApi = retrofit.create<WorkersApi>(WorkersApi::class.java)
        val connection = workerApi.getWorker(Links.API + Links.WORKERS + "/" + id)
        connection.enqueue(object : Callback<WorkerModel> {
            override fun onResponse(call: Call<WorkerModel>?, response: Response<WorkerModel>?) {
                if (response!!.isSuccessful) {
                    val worker = response.body()
                    workerRate.rating = worker?.rate!!
                    workerNameTV.text = worker.name
                    phoneTV.text = worker.phone
                    emailTV.text = worker.email
                    addressTV.text = worker.address
                    bioTV.text = worker.bio
                    Picasso.get().load(worker.image).into(workerCIV)

                    getLocation.setOnClickListener {
                        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", worker.latitude, worker.longitude)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        startActivity(intent)
                    }

                    callIV.setOnClickListener {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${worker.phone}")
                        startActivity(intent)
                    }
                } else {
                    Helper.getErrorMessage(this@WorkerActivity, response)
                }
            }

            override fun onFailure(call: Call<WorkerModel>?, t: Throwable?) {

            }

        })

    }
}
