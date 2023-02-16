package com.example.pa1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {

    var textloginSuccess: TextView? = null
    var imageThumbnail: ImageView? = null
    var fn: String? = null
    var ln: String? = null
    var login: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        textloginSuccess = findViewById<View>(R.id.loginText) as TextView

        val receivedIntent = intent

        fn = receivedIntent.getStringExtra("FN_data")
        ln = receivedIntent.getStringExtra("LN_data")
        login = "$fn $ln is logged in!"

        textloginSuccess!!.text = login
    }
}