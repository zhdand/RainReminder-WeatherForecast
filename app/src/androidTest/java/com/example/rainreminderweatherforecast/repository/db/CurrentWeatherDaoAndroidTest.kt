package com.example.rainreminderweatherforecast.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.getOrAwaitValueAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrentWeatherDaoAndroidTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   private lateinit var database: WeatherDatabase
   private lateinit var daoCurrentWeather: ICurrentWeatherDao

   @Before
   fun setup() {
      database = Room.inMemoryDatabaseBuilder(
         ApplicationProvider.getApplicationContext(),
         WeatherDatabase::class.java
      ).allowMainThreadQueries()
         .build()
      daoCurrentWeather = database.getCurrentWeatherDao()
   }

   @After
   fun teardown() {
      database.close()
   }

   @Test
   fun insertCurrentWeather_returnTrue() = runBlocking {
      val currentWeatherItem = CurrentWeather(
         1, "26.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "now", "Cloudy"
      )
      daoCurrentWeather.updateOrInsertCurrentWeather(currentWeatherItem)
      val currentWeather = daoCurrentWeather.getCurrentWeather().getOrAwaitValueAndroidTest()
      assertThat(currentWeather).isEqualTo(currentWeatherItem)
   }

   @Test
   fun updateCurrentWeather_returnTrue() = runBlocking {
      val currentWeatherItem = CurrentWeather(
         1, "26.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "now", "Cloudy"
      )
      val currentWeatherItem2 = CurrentWeather(
         1, "30.05.2021", "London",
         15.0, 5.0, 12.0, "icon", "now", "Cloudy"
      )
      daoCurrentWeather.updateOrInsertCurrentWeather(currentWeatherItem)
      daoCurrentWeather.updateOrInsertCurrentWeather(currentWeatherItem2)
      val currentWeather = daoCurrentWeather.getCurrentWeather().getOrAwaitValueAndroidTest()
      assertThat(currentWeather).isEqualTo(currentWeatherItem2)
   }

}