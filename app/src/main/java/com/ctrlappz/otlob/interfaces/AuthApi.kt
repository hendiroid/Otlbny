package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.UserModel
import com.ctrlappz.otlob.models.WorkerModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST
    fun login(@FieldMap postBody: Map<String, String>, @Url url: String): Call<UserModel>

    @FormUrlEncoded
    @POST
    fun workerLogin(@FieldMap postBody: Map<String, String>, @Url url: String): Call<WorkerModel>

    @FormUrlEncoded
    @POST
    fun signUp(@FieldMap postBody: Map<String, String>, @Url url: String): Call<UserModel>

    @FormUrlEncoded
    @POST
    fun workerSignUp(@FieldMap postBody: Map<String, String>, @Url url: String): Call<WorkerModel>

    @FormUrlEncoded
    @POST
    fun logout(@Field("player_id") playerId: String, @Header("Authorization") apiToken: String, @Url url: String): Call<ResponseBody>

}