package com.ctrlappz.otlob.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

// Created by hend on 16-4-18.

class ProfileInfo constructor(context: Context) {

     var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences("profile_info", MODE_PRIVATE)
    }

    fun saveInformation(map: HashMap<String, String?>) {
        val editor = sharedPreferences!!.edit()

        for (i in 0 until map.size) {
            editor.putString(map.keys.toTypedArray()[i], map.values.toTypedArray()[i])
        }

        editor.apply()
    }

    fun getInformation(key: String): String? {
        return sharedPreferences!!.getString(key, null)
    }
}