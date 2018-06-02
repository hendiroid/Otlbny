package com.ctrlappz.otlob.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.adapters.JobsAdapter
import com.ctrlappz.otlob.interfaces.ServicesApi
import com.ctrlappz.otlob.models.CategoryModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
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
        val layoutManager = LinearLayoutManager(this@NewAccountWorkerActivity)


        chooseJob.setOnClickListener {
            val dialog = Dialog(this@NewAccountWorkerActivity)
            dialog.setContentView(R.layout.jobs_dialog)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            dialog.jobsRV.layoutManager = layoutManager
            getCategories(dialog)

            dialog.show()

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
}
