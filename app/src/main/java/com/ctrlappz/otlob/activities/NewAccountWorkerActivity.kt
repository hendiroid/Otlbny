package com.ctrlappz.otlob.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.adapters.JobsAdapter
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.interfaces.ServicesApi
import com.ctrlappz.otlob.models.CategoryModel
import com.ctrlappz.otlob.models.WorkerModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_new_account_worker.*
import kotlinx.android.synthetic.main.jobs_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewAccountWorkerActivity : AppCompatActivity() {
    private lateinit var servicesApi: ServicesApi
    private lateinit var categoriesList: ArrayList<CategoryModel>
    private lateinit var categoriesAdapter: JobsAdapter
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account_worker)

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        servicesApi = retrofit.create<ServicesApi>(ServicesApi::class.java)
        loginTextView.setOnClickListener {
            startActivity(Intent(this@NewAccountWorkerActivity, LoginActivity::class.java))
        }

        locationIV.setOnClickListener {
            val intent = Intent(this@NewAccountWorkerActivity, MapsActivity::class.java)
            startActivityForResult(intent, 300)
        }
        chooseJob.setOnClickListener {
            val dialog = Dialog(this@NewAccountWorkerActivity)
            dialog.setContentView(R.layout.jobs_dialog)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            val layoutManager = LinearLayoutManager(this@NewAccountWorkerActivity)


            dialog.jobsRV.layoutManager = layoutManager
            getCategories(dialog)
            dialog.doneBT.setOnClickListener {
                if (categoriesAdapter.selectedCategory == null) {
                    Toast.makeText(this@NewAccountWorkerActivity, "قم باختيار مهنتك", Toast.LENGTH_LONG).show()
                } else {
                    id = categoriesAdapter.selectedCategory!!.id
                    dialog.dismiss()
                }

            }
            dialog.show()

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
                Toast.makeText(this@NewAccountWorkerActivity, "password and retype password not matches", Toast.LENGTH_LONG).show()
            } else {
                val postBody = HashMap<String, String>()
                postBody["name"] = name
                postBody["email"] = email
                postBody["password"] = password
                postBody["phone"] = phone
                postBody["city"] = city
                postBody["place"] = place
                postBody["service_id"] = id
                postBody["longitude"] = lng.toString()
                postBody["latitude"] = lat.toString()

                signUp(postBody)
            }
        }
    }

    private fun getCategories(dialog: Dialog) {
        val connection = servicesApi.getServices(Links.API + Links.SERVICES)
        connection.enqueue(object : Callback<ArrayList<CategoryModel>> {
            override fun onResponse(call: Call<ArrayList<CategoryModel>>?, response: Response<ArrayList<CategoryModel>>?) {
                if (response!!.isSuccessful) {
                    categoriesList = response.body()!!
                    categoriesAdapter = JobsAdapter(categoriesList)
                    dialog.jobsRV.adapter = categoriesAdapter
                } else {
                    Helper.getErrorMessage(this@NewAccountWorkerActivity, response)
                }
            }

            override fun onFailure(call: Call<ArrayList<CategoryModel>>?, t: Throwable?) {
            }
        })
    }

    private fun signUp(postBody: Map<String, String>) {
        val dialog = Helper.progressDialog(this@NewAccountWorkerActivity, "انشاء...")
        dialog.show()
        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
        val connection = authApi.workerSignUp(postBody, Links.API + Links.WORKER + Links.SIGN_UP)
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

                    val map = HashMap<String, String?>()
                    map["id"] = id
                    map["name"] = name
                    map["apiToken"] = apiToken
                    map["profile"] = image
                    map["phone"] = phone
                    map["email"] = email
                    val profileInfo = ProfileInfo(applicationContext)
                    profileInfo.saveInformation(map)
                    startActivity(Intent(this@NewAccountWorkerActivity, MainActivity::class.java))
                    finish()
                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@NewAccountWorkerActivity, response)
                }
            }

            override fun onFailure(call: Call<WorkerModel>?, t: Throwable?) {
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
