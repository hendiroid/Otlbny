package com.ctrlappz.otlob.models

class UserModel {

    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var apiToken: String
    lateinit var phone: String
    lateinit var image: String
    lateinit var city: String
    var address: String? = null
    var longitude: Double? = null
    var latitude: Double? = null


}