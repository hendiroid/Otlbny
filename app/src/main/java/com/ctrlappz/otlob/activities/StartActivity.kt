package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        worker.setOnClickListener {

            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            intent.putExtra("type", "worker")
            startActivity(intent)
            finish()
        }
        user.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            intent.putExtra("type", "user")
            startActivity(intent)
            finish()
        }
    }
}
