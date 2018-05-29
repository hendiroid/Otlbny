package com.ctrlappz.otlob.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.fragments.HomeFragment
import com.ctrlappz.otlob.fragments.MapFragment
import com.ctrlappz.otlob.fragments.ProfileFragment
import com.ctrlappz.otlob.interfaces.AuthApi
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import com.ctrlappz.otlob.utils.ProfileInfo
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFrag(HomeFragment())
            }
            R.id.navigation_map -> {
                openFrag(MapFragment())
            }
            R.id.navigation_profile -> {
                openFrag(ProfileFragment())
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFrag(HomeFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_suggestion -> startActivity(Intent(this@MainActivity, SuggestionActivity::class.java))
            R.id.action_about -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
//            R.id.change_password -> startActivity(Intent(this@MainActivity, ChangePasswordActivity::class.java))
//            R.id.action_account_setting -> startActivity(Intent(this@MainActivity, AccountSettingActivity::class.java))
            R.id.action_logout -> logout()
        }
        return false
    }

    private fun logout() {

        val dialog = Helper.progressDialog(this@MainActivity, " Signing out...")
        dialog.show()
        val profileInformation = ProfileInfo(this@MainActivity)
        val apiToken = profileInformation.getInformation("apiToken")

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val playerId = "test"

        val authApi = retrofit.create(AuthApi::class.java)
        val connection = authApi.logout(playerId, "Bearer $apiToken", Links.API)
        connection.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response!!.isSuccessful) {
                    val map = HashMap<String, String?>()
                    map["apiToken"] = null

                    profileInformation.saveInformation(map)
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()

                } else {
                    dialog.dismiss()
                    Helper.getErrorMessage(this@MainActivity, response)
                }

            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                dialog.dismiss()
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }

        })

    }
}
