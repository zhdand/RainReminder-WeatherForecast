package com.example.rainreminderweatherforecast.repository.providers.internet_connection

import javax.inject.Inject

class IsNetworkAvailableProvider
@Inject constructor(private val isNetworkAvailable: IsNetworkAvailableLiveData)
{
   fun getIsNetworkAvailable() = isNetworkAvailable
}