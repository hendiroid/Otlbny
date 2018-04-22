package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignActivity, LoginActivity::class.java))
        }
    }
}