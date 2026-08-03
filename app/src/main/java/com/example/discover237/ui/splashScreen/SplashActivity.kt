package com.example.discover237.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.ui.facts.FactsActivity

class SplashActivity : AppCompatActivity() {

    private val splashDelay = 1200L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FactsActivity::class.java))
            finish()
        }, splashDelay)
    }
}