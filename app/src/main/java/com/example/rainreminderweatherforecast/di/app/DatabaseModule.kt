package com.example.rainreminderweatherforecast.di.app

import android.content.Context
import com.example.rainreminderweatherforecast.repository.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

   @Provides
   @Singleton
   fun provideRoomDatabase(context: Context): WeatherDatabase =
      WeatherDatabase.getInstance(context)

   @Provides
   @Singleton
   fun provideCurrentWeatherDao(database: WeatherDatabase): ICurrentWeatherDao = database.getCurrentWeatherDao()

   @Provides
   @Singleton
   fun provideFutureWeatherDao(database: WeatherDatabase): IFutureWeatherDao = database.getFutureWeatherDao()

   @Provides
   @Singleton
   fun provideRemindersDao(database: WeatherDatabase): IReminderRainDaysDao = database.getReminderRainDaysDao()

}