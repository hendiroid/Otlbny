package com.ctrlappz.otlob.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.adapters.WorkersAdapter
import com.ctrlappz.otlob.interfaces.WorkersApi
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkersActivity : AppCompatActivity() {

    private lateinit var id: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var workerApi: WorkersApi
    private lateinit var workersList: ArrayList<WorkerModel>
    private lateinit var workersAdapter: WorkersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workers)
        recyclerView = findViewById(R.id.workersRV)
        val layoutManager = LinearLayoutManager(this@WorkersActivity)
        recyclerView.layoutManager = layoutManager

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        workerApi = retrofit.create<WorkersApi>(WorkersApi::class.java)
        id = intent.extras!!.getString("id")
        getWorkers(id)
    }
    private fun getWorkers(id: String) {
        val connection = workerApi.getWorkersInCategory(Links.API +
                Links.WORKERS + "?service_id=" + id)
        connection.enqueue(object : Callback<ArrayList<WorkerModel>> {
            override fun onResponse(call: Call<ArrayList<WorkerModel>>?,
                                    response: Response<ArrayList<WorkerModel>>?) {
                if (response!!.isSuccessful) {
                    workersList = response.body()!!
                    workersAdapter = WorkersAdapter(workersList, this@WorkersActivity)
                    recyclerView.adapter = workersAdapter
                } else {
                    Helper.getErrorMessage(this@WorkersActivity, response)
                }
            }
            override fun onFailure(call: Call<ArrayList<WorkerModel>>?, t: Throwable?) {
            }
        })

    }
}
