package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import kotlinx.android.synthetic.main.activity_sign.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignActivity, LoginActivity::class.java))
        }

        createBT.setOnClickListener {
            val name = nameET.text.toString().trim()
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
                postBody["password"] = password
                postBody["phone"] = phone
                postBody["city"] = city
                postBody["place"] = place

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

    }
}
