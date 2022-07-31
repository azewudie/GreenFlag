package com.example.greanflag

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    var splash:TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        splash = findViewById(R.id.tv_app_name)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            startActivity(Intent(this,introActivity::class.java))
            finish()
        },2500)


    }
}