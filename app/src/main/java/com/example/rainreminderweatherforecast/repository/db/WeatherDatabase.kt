package com.example.rainreminderweatherforecast.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay

@Database(
   entities = [CurrentWeather::class, FutureWeather::class, ReminderRainDay::class],
   version = 1,
   exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {

   abstract fun getCurrentWeatherDao(): ICurrentWeatherDao
   abstract fun getFutureWeatherDao(): IFutureWeatherDao
   abstract fun getReminderRainDaysDao(): IReminderRainDaysDao

   companion object {
      private const val DATABASE_NAME: String = "weather_database"

      const val TABLE_NAME_CURRENT_WEATHER: String = "weather"
      const val TABLE_NAME_FUTURE_WEATHER: String = "future_weather"
      const val TABLE_NAME_REMINDER_RAIN: String = "reminder_rain_day"

      @Volatile
      private var INSTANCE: WeatherDatabase? = null

      fun getInstance(context: Context): WeatherDatabase {
         return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
               context,
               WeatherDatabase::class.java,
               DATABASE_NAME
            )
               .fallbackToDestructiveMigration()
               .build()

            INSTANCE = instance
            instance
         }
      }

   }

}

