package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.WorkerModel
import retrofit2.Call
import retrofit2.http.*

interface WorkersApi {

    @GET
    fun getWorkers(@Url url: String): Call<Array<WorkerModel>>

    @GET
    fun getWorkersInCategory(@Url url: String): Call<ArrayList<WorkerModel>>

    @GET
    fun getWorker(@Url url: String): Call<WorkerModel>

}