package com.ctrlappz.otlob.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import com.ctrlappz.otlob.R
import org.json.JSONObject
import retrofit2.Response
import java.util.*

object Helper {

    fun getInformation(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences("profile_info", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun getErrorMessage(context: Context, response: Response<*>) {
        val errorBody = JSONObject(response.errorBody()!!.string())
        Toast.makeText(context, errorBody.get("message").toString(), Toast.LENGTH_LONG).show()
    }


    @SuppressLint("Recycle")
    fun getRealPathFromURI(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }
    fun progressDialog(context: Context, title: String): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.progress_dialog)
        val progressTV = dialog.findViewById<TextView>(R.id.progress_text)
        progressTV.text = title
        dialog.setCancelable(false)

        return dialog
    }

}