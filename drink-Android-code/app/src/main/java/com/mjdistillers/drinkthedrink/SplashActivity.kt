package com.mjdistillers.drinkthedrink

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            Log.i("dd", "Extra SP:" + extras.getString(DtdConstants.NOTIFICATION_KEY))
        }


        setContentView(R.layout.content_splash)
        Handler().postDelayed(Runnable {
            var intent = Intent(SplashActivity@this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left)
        },2000)
    }

}
