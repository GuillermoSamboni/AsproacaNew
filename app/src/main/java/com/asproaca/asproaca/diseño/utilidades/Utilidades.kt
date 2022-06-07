package com.asproaca.asproaca.diseño.utilidades

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

class Utilidades {
    companion object {
        fun obtenerEstadoDeRed(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) == true || capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) == true || capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) == true) { return true } }
            return false
        }
    }
}