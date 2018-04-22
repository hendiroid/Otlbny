package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.models.UserModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createAccountTV.setOnClickListener {
            startActivity(Intent(this@LoginActivity, StartActivity::class.java))
        }

        sign_button.setOnClickListener {
            val email = email_edit.text.toString().trim()
            val password = passwordET.text.toString().trim()

            val postBody = HashMap<String, String>()
            postBody["email"] = email
            postBody["password"] = password
            postBody["player_id"] = "test"

            login(postBody)
        }
    }

    private fun login(postBody: Map<String, String>) {

        val dialog = Helper.progressDialog(this@LoginActivity, "Signing in...")
        dialog.show()

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
        val connection = authApi.login(postBody, Links.API + Links.LOGIN)
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
                    map["firstName"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email

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
}
