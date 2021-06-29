package com.example.rainreminderweatherforecast.repository.providers.internet_connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import javax.inject.Inject

class IsNetworkAvailableLiveData
@Inject
constructor(context: Context) : LiveData<ResultNetworkAvailableState>() {

   private val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

   lateinit var networkCallback: ConnectivityManager.NetworkCallback

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onActive() {
      super.onActive()
      if (connectivityManager.activeNetwork == null){
         postValue(ResultNetworkAvailableState.NetworkUnavailable)
      }
      networkCallback = object : ConnectivityManager.NetworkCallback() {
         override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(ResultNetworkAvailableState.NetworkAvailable)
         }

         override fun onLost(network: Network) {
            super.onLost(network)
            postValue(ResultNetworkAvailableState.NetworkUnavailable)
         }

         override fun onUnavailable() {
            super.onUnavailable()
            postValue(ResultNetworkAvailableState.NetworkUnavailable)
         }
      }
      val networkRequest = NetworkRequest.Builder().build()

      connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
   }

   override fun onInactive() {
      super.onInactive()
      connectivityManager.unregisterNetworkCallback(networkCallback)
      postValue(ResultNetworkAvailableState.NetworkEmptyState)
   }
}

