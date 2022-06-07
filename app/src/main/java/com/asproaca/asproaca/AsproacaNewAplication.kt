package com.asproaca.asproaca
import android.annotation.SuppressLint
import android.app.Application

class AsproacaNewAplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var preferencia: Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        preferencia = Preferencias(applicationContext)
    }


}