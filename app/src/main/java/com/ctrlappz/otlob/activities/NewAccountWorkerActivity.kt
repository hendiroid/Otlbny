package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import kotlinx.android.synthetic.main.activity_new_account_worker.*

class NewAccountWorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account_worker)

        loginTextView.setOnClickListener {
            startActivity(Intent(this@NewAccountWorkerActivity, LoginActivity::class.java))
        }

        chooseJob.setOnClickListener {

        }
    }
}
