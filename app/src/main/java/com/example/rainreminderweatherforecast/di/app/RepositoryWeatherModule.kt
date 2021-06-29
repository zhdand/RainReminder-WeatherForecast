package com.example.rainreminderweatherforecast.di.app

import com.example.rainreminderweatherforecast.domain.*
import com.example.rainreminderweatherforecast.repository.RepositoryCurrentWeatherImpl
import com.example.rainreminderweatherforecast.repository.RepositoryRemindersImpl
import com.example.rainreminderweatherforecast.repository.RepositoryFutureWeatherImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryWeatherModule {

   @Binds
   @Singleton
   abstract fun bindRepositoryCurrentWeather(repository: RepositoryCurrentWeatherImpl): IRepositoryCurrentWeather

   @Binds
   @Singleton
   abstract fun bindRepositoryFutureWeather(repository: RepositoryFutureWeatherImpl): IRepositoryFutureWeather

   @Binds
   @Singleton
   abstract fun bindRepositoryReminders(repository: RepositoryRemindersImpl): IRepositoryReminders

}