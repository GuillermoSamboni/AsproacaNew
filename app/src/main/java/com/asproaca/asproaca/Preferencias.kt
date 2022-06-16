package com.asproaca.asproaca

import android.content.Context

class Preferencias(val context: Context) {
    val nombrePreferencia = "preferencia"
    val nombreUsuario = "nombreUsuario"
    val idUsuario = "idUsuario"
    val rolUser = "rol"

    val almacenamiento = context.getSharedPreferences(nombrePreferencia, 0)

    fun guardarUsuario(name: String) {
        almacenamiento.edit().putString(nombreUsuario, name).apply()
    }

    fun obtenerUsuario(): String {
        return almacenamiento.getString(nombreUsuario, "")!!
    }

    fun guardarIdUsuario(id: String): Boolean {
        almacenamiento.edit().putString(idUsuario, id).apply()
        return true
    }

    fun obtenerIdUsuario(): String {
        return almacenamiento.getString(idUsuario, "")!!
    }

    fun guardarRol(rolUserParam: String):Boolean {
        almacenamiento.edit().putString(rolUser, rolUserParam).apply()
        return true
    }

    fun obtenerRol(): String {
        return almacenamiento.getString(rolUser, "")!!
    }

    fun borrarPreferencias() {
        almacenamiento.edit().clear().apply()
    }
    fun borrarPreferenciasUser() {
        almacenamiento.edit().clear().apply()
    }

    fun esPrimeraVezEnPrincipal(context: Context): Boolean {
        val shp = almacenamiento
        val shap = context.getSharedPreferences(nombrePreferencia, 0)
        val firstTime = shp.getBoolean("primeraVezPrincipal", true)

        if (firstTime) shp.edit().putBoolean("primeraVezPrincipal", false).apply()

        return firstTime
    }

    fun esPrimeraVezEnFincas(context: Context): Boolean {
        val shp = almacenamiento
        val shap = context.getSharedPreferences(nombrePreferencia, 0)
        val firstTime = shp.getBoolean("primeraVezFinca", true)

        if (firstTime) shp.edit().putBoolean("primeraVezFinca", false).apply()

        return firstTime
    }

}