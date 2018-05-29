package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.CategoryModel
import retrofit2.Call
import retrofit2.http.*

interface ServicesApi {

    @GET
    fun getServices(@Url url: String): Call<ArrayList<CategoryModel>>
}