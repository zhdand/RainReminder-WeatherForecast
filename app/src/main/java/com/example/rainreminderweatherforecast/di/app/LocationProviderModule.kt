package com.example.rainreminderweatherforecast.di.app

import android.content.Context
import com.example.rainreminderweatherforecast.repository.providers.location.ILocationProvider
import com.example.rainreminderweatherforecast.repository.providers.location.LocationProviderImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationProviderModule {

   @Provides
   @Singleton
   fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
      return LocationServices.getFusedLocationProviderClient(context)
   }

   @Provides
   @Singleton
   fun provideLocationProvider(
      fusedLocationProviderClient: FusedLocationProviderClient
   ): ILocationProvider {
      return LocationProviderImpl(fusedLocationProviderClient)
   }
}