package com.example.rainreminderweatherforecast.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather

@Dao
interface ICurrentWeatherDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun updateOrInsertCurrentWeather(weather: CurrentWeather)

   @Query("SELECT * FROM ${WeatherDatabase.TABLE_NAME_CURRENT_WEATHER}")
   fun getCurrentWeather(): LiveData<CurrentWeather>

}
