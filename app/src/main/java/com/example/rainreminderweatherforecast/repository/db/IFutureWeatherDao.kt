package com.example.rainreminderweatherforecast.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rainreminderweatherforecast.domain.models.FutureWeather

@Dao
interface IFutureWeatherDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun updateOrInsertFutureWeather(futureWeather: List<FutureWeather>)

   @Query("SELECT * FROM ${WeatherDatabase.TABLE_NAME_FUTURE_WEATHER}")
   fun getFutureWeather(): LiveData<List<FutureWeather>>

}