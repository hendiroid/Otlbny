package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.interfaces.UserApi
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_suggestion.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuggestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)

        send_suggestion_button.setOnClickListener {

            sendSuggestion()
        }
    }

    private fun sendSuggestion() {

        val dialog = Helper.progressDialog(this@SuggestionActivity, "ارسال...")
        dialog.show()
        val profileInfo = ProfileInfo(this@SuggestionActivity)

        val apiToken = profileInfo.getInformation("apiToken")
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val userApi = retrofit.create<UserApi>(UserApi::class.java)
        val connection = userApi.sendSuggestion(suggestion_edit_text.text.toString().trim(), "Bearer $apiToken", Links.API + Links.SUGGESTIONS)
        connection.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response!!.isSuccessful) {
                    Toast.makeText(this@SuggestionActivity, "تم ارسال الاقتراح بنجاح!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@SuggestionActivity, MainActivity::class.java))
                    finish()
                } else {
                    Helper.getErrorMessage(this@SuggestionActivity, response)
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

        })

    }
}
