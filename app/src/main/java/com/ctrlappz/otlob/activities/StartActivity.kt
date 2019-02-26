package com.ctrlappz.otlob.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }


        worker.setOnClickListener {

            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            intent.putExtra("type", "worker")
            val map = HashMap<String, String?>()
            map["type"] = "workers"
            val profileInfo = ProfileInfo(applicationContext)
            profileInfo.saveInformation(map)
            startActivity(intent)
            finish()
        }
        user.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            intent.putExtra("type", "user")
            val map = HashMap<String, String?>()
            map["type"] = "users"
            val profileInfo = ProfileInfo(applicationContext)
            profileInfo.saveInformation(map)
            startActivity(intent)
            finish()
        }
    }
}
