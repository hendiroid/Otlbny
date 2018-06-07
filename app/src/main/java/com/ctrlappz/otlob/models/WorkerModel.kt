package com.ctrlappz.otlob.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WorkerModel {
    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var apiToken: String
    lateinit var phone: String
    lateinit var image: String
    lateinit var city: String
    lateinit var address: String

    @SerializedName("service_id")
    @Expose
    lateinit var service: String
    lateinit var bio: String

    @SerializedName("work_hours")
    @Expose
    lateinit var hours: String

    var longitude: Double? = null
    var latitude: Double? = null
    var rate: Float? = null

}