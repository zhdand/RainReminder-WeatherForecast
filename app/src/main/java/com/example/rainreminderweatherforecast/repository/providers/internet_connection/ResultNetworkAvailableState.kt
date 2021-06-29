package com.example.rainreminderweatherforecast.repository.providers.internet_connection

sealed class ResultNetworkAvailableState {
   object NetworkAvailable: ResultNetworkAvailableState()
   object NetworkUnavailable:  ResultNetworkAvailableState()
   object NetworkEmptyState: ResultNetworkAvailableState()
}