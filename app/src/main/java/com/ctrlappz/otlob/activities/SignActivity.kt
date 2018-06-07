package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.models.UserModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_sign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignActivity : AppCompatActivity() {

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignActivity, LoginActivity::class.java))
        }
        locationIV.setOnClickListener {
            val intent = Intent(this@SignActivity, MapsActivity::class.java)
            startActivityForResult(intent, 300)

        }

        createBT.setOnClickListener {
            val name = nameET.text.toString().trim()
            val email = emailET.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val retypePassword = retypePasswordEditText.text.toString().trim()
            val phone = phoneET.text.toString().trim()
            val city = cityET.text.toString().trim()
            val place = placeET.text.toString().trim()

            if (password != retypePassword) {
                Toast.makeText(this@SignActivity, "password and retype password not matches", Toast.LENGTH_LONG).show()
            } else {
                val postBody = HashMap<String, String>()
                postBody["name"] = name
                postBody["email"] = email
                postBody["password"] = password
                postBody["phone"] = phone
                postBody["city"] = city
                postBody["place"] = place
                postBody["longitude"] = lng.toString()
                postBody["latitude"] = lat.toString()

                signUp(postBody)
            }

        }
    }

    private fun signUp(postBody: Map<String, String>) {

        val dialog = Helper.progressDialog(this@SignActivity, "انشاء...")
        dialog.show()

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
        val connection = authApi.signUp(postBody, Links.API + Links.USER + Links.SIGN_UP)
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

                    val map = HashMap<String, String?>()
                    map["id"] = id
                    map["name"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email

                    val profileInfo = ProfileInfo(applicationContext)
                    profileInfo.saveInformation(map)

                    startActivity(Intent(this@SignActivity, MainActivity::class.java))
                    finish()


                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@SignActivity, response)
                }

            }

            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                dialog.dismiss()
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
