package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.WorkerModel
import retrofit2.Call
import retrofit2.http.*

interface WorkersApi {

    @GET
    fun getWorkers(@Url url: String): Call<Array<WorkerModel>>

}