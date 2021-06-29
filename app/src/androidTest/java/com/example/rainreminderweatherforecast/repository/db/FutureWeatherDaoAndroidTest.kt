package com.example.rainreminderweatherforecast.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
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
class FutureWeatherDaoAndroidTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   private lateinit var database: WeatherDatabase
   private lateinit var dao: IFutureWeatherDao

   @Before
   fun setup() {
      database = Room.inMemoryDatabaseBuilder(
         ApplicationProvider.getApplicationContext(),
         WeatherDatabase::class.java
      ).allowMainThreadQueries()
         .build()
      dao = database.getFutureWeatherDao()
   }

   @After
   fun teardown() {
      database.close()
   }

   @Test
   fun insertFutureWeatherWhereKeyIsId_returnTrue() = runBlocking {
      val futureWeatherItem1 = FutureWeather(
         1, "26.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "Sunny"
      )
      val futureWeatherItem2 = FutureWeather(
         2, "27.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "Cloudy"
      )
      val futureWeatherList = listOf(futureWeatherItem1, futureWeatherItem2)

      dao.updateOrInsertFutureWeather(futureWeatherList)

      val currentWeather = dao.getFutureWeather().getOrAwaitValueAndroidTest()
      assertThat(currentWeather).isEqualTo(futureWeatherList)
   }

   @Test
   fun updateFutureWeatherWhereKeyIsId_returnTrue() = runBlocking {
      val futureWeatherItem1 = FutureWeather(
         1, "26.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "Sunny"
      )
      val futureWeatherItem2 = FutureWeather(
         2, "26.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "Cloudy"
      )
      val futureWeatherItem3 = FutureWeather(
         1, "30.05.2021", "London",
         10.0, 5.0, 12.0, "icon", "Cloudy"
      )
      val futureWeatherList1 = listOf(futureWeatherItem1, futureWeatherItem2)
      val futureWeatherList2 = listOf(futureWeatherItem3, futureWeatherItem2)

      dao.updateOrInsertFutureWeather(futureWeatherList1)
      dao.updateOrInsertFutureWeather(futureWeatherList2)

      val currentWeather = dao.getFutureWeather().getOrAwaitValueAndroidTest()
      assertThat(currentWeather).isEqualTo(futureWeatherList2)
   }

}