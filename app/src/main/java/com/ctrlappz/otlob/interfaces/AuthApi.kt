package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.UserModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface AuthApi {

    @FormUrlEncoded
    @POST
    fun login(@FieldMap postBody: Map<String, String>, @Url url: String): Call<UserModel>

    @FormUrlEncoded
    @POST
    fun signUp(@FieldMap postBody: Map<String, String>, @Url url: String): Call<UserModel>


}