package com.example.rainreminderweatherforecast.di.app

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.IsNetworkAvailableLiveData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IsNetworkAvailableProviderModule {

   @Provides
   @Singleton
   fun provideIsNetworkConnectedLiveData(context: Context): LiveData<ResultNetworkAvailableState> {
      return IsNetworkAvailableLiveData(context)
   }

}