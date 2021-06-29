package com.example.rainreminderweatherforecast.repository.providers.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocationProviderImpl
@Inject constructor(
   private val fusedLocationProviderClient: FusedLocationProviderClient
) : ILocationProvider {
   override var currentLocationLatLng: MutableStateFlow<LocationLatLngModel?> =
      MutableStateFlow(null)

   @SuppressLint("MissingPermission")
   override fun getLastDeviceLocation() {
      fusedLocationProviderClient.lastLocation
         .addOnSuccessListener { lastLocation ->
            if (lastLocation != null) {
               currentLocationLatLng.value = LocationLatLngModel(
                  lastLocation.latitude,
                  lastLocation.longitude
               )
            } else {
               startLocationUpdates()
            }
         }
         .addOnFailureListener { }
   }

   @SuppressLint("MissingPermission")
   override fun startLocationUpdates() {
      fusedLocationProviderClient.requestLocationUpdates(
         locationRequest,
         locationCallback,
         Looper.getMainLooper()
      )
   }

   private val locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
         locationResult ?: return
         fusedLocationProviderClient.removeLocationUpdates(this)

         currentLocationLatLng.value = LocationLatLngModel(
            locationResult.lastLocation.latitude,
            locationResult.lastLocation.longitude
         )
      }
   }

   private val locationRequest: LocationRequest = LocationRequest.create().apply {
      interval = 5000
      fastestInterval = 500
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
   }

}