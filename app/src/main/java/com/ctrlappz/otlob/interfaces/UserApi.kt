package com.ctrlappz.otlob.interfaces

import com.ctrlappz.otlob.models.UserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST
    fun sendSuggestion(@Field("content") content: String, @Header("Authorization") apiToken: String, @Url url: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun updateInfo(@FieldMap postBody: Map<String, String>, @Url url: String): Call<UserModel>
}