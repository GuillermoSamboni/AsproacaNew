package com.asproaca.asproaca.diseño.utilidades

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationRequest
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener

class Localizacion(val context: Context) : LocationCallback() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var myLastUbicationLocation: android.location.Location

    /**
     * Iniciamos la busqueda de la localizacion del dispositivo
     */
    @SuppressLint("MissingPermission")
    fun startRequest() {
        val settingClient = LocationServices.getSettingsClient(context)
        val myLocationBuilder = LocationSettingsRequest.Builder()

        val myUbi = LocationRequest()
        myUbi.priority
        myLocationBuilder.addLocationRequest(myUbi)

        val locationRequest = myLocationBuilder.build()
        settingClient.checkLocationSettings(locationRequest)
            .addOnSuccessListener(OnSuccessListener {
                fusedLocationProviderClient = FusedLocationProviderClient(context)
                fusedLocationProviderClient.requestLocationUpdates(myUbi, this@Localizacion, Looper.getMainLooper())
            }).addOnFailureListener { e ->
                when ((e as ResolvableApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val rae = e; rae.startResolutionForResult(context as Activity, 0)
                    } catch (e: IntentSender.SendIntentException) { Log.e("errorlocation", e.toString()) }
                }
            }
    }

    override fun onLocationResult(p0: LocationResult) {
        super.onLocationResult(p0)
        p0.let { myLastUbicationLocation = p0.lastLocation;
            Constantes2.coordenada_x = myLastUbicationLocation.longitude.toString()
            Constantes2.coordenada_y = myLastUbicationLocation.latitude.toString()

            //Toast.makeText(context, "${myLastUbicationLocation}", Toast.LENGTH_LONG).show()
        }
    }


}