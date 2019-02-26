package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.UserApi
import com.ctrlappz.otlob.interfaces.WorkersApi
import com.ctrlappz.otlob.models.UserModel
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountSettingActivity : AppCompatActivity() {
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        val profileInfo = ProfileInfo(this@AccountSettingActivity)
        type = profileInfo.getInformation("type")
        Picasso.get().load(profileInfo.getInformation("profile")).into(circleImageView)
        nameET.setText(profileInfo.getInformation("name"))
        emailET.setText(profileInfo.getInformation("email"))
        phoneET.setText(profileInfo.getInformation("phone"))
        cityET.setText(profileInfo.getInformation("city"))
        placeET.setText(profileInfo.getInformation("address"))
        timeET.setText(profileInfo.getInformation("work_hours"))

        locationIV.setOnClickListener {
            val intent = Intent(this@AccountSettingActivity, MapsActivity::class.java)
            startActivityForResult(intent, 300)
        }

        saveBT.setOnClickListener {
            if (type!! == "workers") {
                val postBody = HashMap<String, String>()
                postBody["_method"] = "put"
                postBody["name"] = nameET.text.toString().trim()
                postBody["email"] = emailET.text.toString().trim()
                postBody["phone"] = phoneET.text.toString().trim()
                postBody["city"] = cityET.text.toString().trim()
                postBody["place"] = placeET.text.toString().trim()
                postBody["longitude"] = lng.toString()
                postBody["latitude"] = lat.toString()
                postBody["_method"] = "put"

                saveWorker(postBody)
            } else if (type!! == "users") {
                val postBody = HashMap<String, String>()
                postBody["_method"] = "put"
                postBody["name"] = nameET.text.toString().trim()
                postBody["email"] = emailET.text.toString().trim()
                postBody["phone"] = phoneET.text.toString().trim()
                postBody["city"] = cityET.text.toString().trim()
                postBody["place"] = placeET.text.toString().trim()
                postBody["longitude"] = lng.toString()
                postBody["latitude"] = lat.toString()
                saveUser(postBody)

            }
        }

    }

    private fun saveUser(postBody: Map<String, String>) {
        val dialog = Helper.progressDialog(this@AccountSettingActivity, "حفظ التعديلات...")
        dialog.show()
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val userApi = retrofit.create<UserApi>(UserApi::class.java)
        val connection = userApi.updateInfo(postBody, Links.API + Links.USERS)
        connection.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                if (response!!.isSuccessful) {
                    dialog.dismiss()
                    val userModel = response.body()

                    val id = userModel?.id
                    val name = userModel?.name
                    val apiToken = userModel?.apiToken
                    val image = userModel?.image
                    val phone = userModel?.phone
                    val email = userModel?.email
                    val city = userModel?.city
                    val address = userModel?.address

                    val map = HashMap<String, String?>()
                    map["id"] = id
                    map["name"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email
                    map["city"] = city
                    map["address"] = address

                    val profileInfo = ProfileInfo(applicationContext)
                    profileInfo.saveInformation(map)

                    startActivity(Intent(this@AccountSettingActivity, MainActivity::class.java))
                    finish()

                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@AccountSettingActivity, response)
                }

            }

            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                dialog.dismiss()
            }

        })

    }

    private fun saveWorker(postBody: Map<String, String>) {
        val dialog = Helper.progressDialog(this@AccountSettingActivity, "حفظ التعديلات...")
        dialog.show()
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val userApi = retrofit.create<WorkersApi>(WorkersApi::class.java)
        val connection = userApi.updateInfo(postBody, Links.API + Links.WORKERS)
        connection.enqueue(object : Callback<WorkerModel> {
            override fun onResponse(call: Call<WorkerModel>?, response: Response<WorkerModel>?) {
                if (response!!.isSuccessful) {
                    dialog.dismiss()
                    val userModel = response.body()

                    val id = userModel?.id
                    val name = userModel?.name
                    val apiToken = userModel?.apiToken
                    val image = userModel?.image
                    val phone = userModel?.phone
                    val email = userModel?.email
                    val city = userModel?.city
                    val address = userModel?.address
                    val hours = userModel?.hours


                    val map = HashMap<String, String?>()
                    map["id"] = id
                    map["name"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email
                    map["city"] = city
                    map["address"] = address
                    map["work_hours"] = hours

                    val profileInfo = ProfileInfo(applicationContext)
                    profileInfo.saveInformation(map)

                    startActivity(Intent(this@AccountSettingActivity, MainActivity::class.java))
                    finish()

                } else {
                    Helper.getErrorMessage(this@AccountSettingActivity, response)
                }

            }

            override fun onFailure(call: Call<WorkerModel>?, t: Throwable?) {

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && requestCode == 300) {
            val bundle = data.extras
            lat = bundle.getDouble("lat")
            lng = bundle.getDouble("lng")

        }
    }
}
