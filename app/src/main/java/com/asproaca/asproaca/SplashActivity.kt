package com.asproaca.asproaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.asproaca.asproaca.databinding.ActivitySplashBinding
import com.asproaca.asproaca.diseño.principal.ui.autenticacion.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val timeSplash: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnContinuar.setOnClickListener {
            //splashScreen()
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
        }

        /*
            this.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )*/
    }
/*
    private fun splashScreen() {
        Handler().postDelayed(Runnable {
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
        }, timeSplash)
    }*/
}