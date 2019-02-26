package com.ctrlappz.otlob.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.models.UserModel
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this@LoginActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this@LoginActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this@LoginActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE), 10)
            }
        }

        email_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ifViewEmpty()
            }

        })

        passwordET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ifViewEmpty()
            }

        })

        type = intent.extras!!.getString("type")

        createAccountTV.setOnClickListener {
            if (type == "worker") {
                startActivity(Intent(this@LoginActivity, NewAccountWorkerActivity::class.java))

            } else {
                startActivity(Intent(this@LoginActivity, SignActivity::class.java))
            }
        }

        sign_button.setOnClickListener {
            val email = email_edit.text.toString().trim()
            val password = passwordET.text.toString().trim()

            val postBody = HashMap<String, String>()
            postBody["email"] = email
            postBody["password"] = password

            if (type == "worker") {
                workerLogin(postBody)
            } else {
                login(postBody)

            }
        }
    }

    private fun login(postBody: Map<String, String>) {
        val dialog = Helper.progressDialog(this@LoginActivity, "دخول...")
        dialog.show()

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
        val connection = authApi.login(postBody, Links.API + "/" + type + Links.LOGIN)
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

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@LoginActivity, response)
                }
            }

            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                dialog.dismiss()
            }

        })

    }

    private fun workerLogin(postBody: Map<String, String>) {

        val dialog = Helper.progressDialog(this@LoginActivity, "دخول...")
        dialog.show()

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
        val connection = authApi.workerLogin(postBody, Links.API + "/" + type + Links.LOGIN)
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
                    val bio = userModel?.bio
                    val hours = userModel?.hours
                    val city = userModel?.city
                    val address = userModel?.address

                    val map = HashMap<String, String?>()
                    map["id"] = id
                    map["name"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email
                    map["bio"] = bio
                    map["work_hours"] = hours
                    map["address"] = address
                    map["city"] = city

                    val profileInfo = ProfileInfo(applicationContext)
                    profileInfo.saveInformation(map)

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@LoginActivity, response)
                }
            }

            override fun onFailure(call: Call<WorkerModel>?, t: Throwable?) {
                dialog.dismiss()
            }
        })
    }

    private fun ifViewEmpty() {
        val email = email_edit.text.toString().trim()
        val password = passwordET.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            sign_button.isEnabled = false
            sign_button.alpha = .5F
        } else {
            sign_button.isEnabled = true
            sign_button.alpha = 1F
        }
    }
}
