package com.example.rainreminderweatherforecast.repository.providers.location

import kotlinx.coroutines.flow.MutableStateFlow

interface ILocationProvider {

   var currentLocationLatLng: MutableStateFlow<LocationLatLngModel?>

   fun getLastDeviceLocation()

   fun startLocationUpdates()

}