package com.example.githab_user.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.githab_user.R

class SplashScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val midAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val botAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        val ttv: TextView = findViewById(R.id.toptextView)
        val mtv: ImageView = findViewById(R.id.dota2)
        val btv: TextView = findViewById(R.id.botTextView)

        ttv.startAnimation(topAnimation)
        mtv.startAnimation(midAnimation)
        btv.startAnimation(botAnimation)

        val timeout = 4000
        val homeintent = Intent(this@SplashScreen, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(homeintent)
            finish()
        },timeout.toLong())

    }
}