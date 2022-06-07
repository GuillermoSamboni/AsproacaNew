package com.asproaca.asproaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.asproaca.asproaca.diseño.autenticacion.MainActivity

class SplashActivity : AppCompatActivity() {
    private val timeSplash: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        splashScreen()

    }

    private fun splashScreen() {
        Handler().postDelayed(Runnable {
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
        }, timeSplash)
    }
}