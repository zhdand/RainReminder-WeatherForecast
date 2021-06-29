package com.example.rainreminderweatherforecast.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import com.example.rainreminderweatherforecast.getOrAwaitValueAndroidTest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ReminderRainDaysDaoAndroidTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   private lateinit var database: WeatherDatabase
   private lateinit var dao: IReminderRainDaysDao

   @Before
   fun setup() {
      database = Room.inMemoryDatabaseBuilder(
         ApplicationProvider.getApplicationContext(),
         WeatherDatabase::class.java
      ).allowMainThreadQueries()
         .build()
      dao = database.getReminderRainDaysDao()
   }

   @After
   fun teardown() {
      database.close()
   }

   @Test
   fun insertReminderRainDaysWhereKeyIsDate_returnTrue() = runBlocking {
      val reminder1 = ReminderRainDay(123, "26.05.2021", "London", 7, 30, "Sunny")
      val reminder2 = ReminderRainDay(23, "27.05.2021", "London", 9, 30, "Sunny")
      val reminder3 = ReminderRainDay(2351, "30.05.2021", "London", 8, 30, "Sunny")
      val remindersList = listOf(reminder1, reminder2,reminder3)

      dao.updateOrInsertAlarm(reminder1)
      dao.updateOrInsertAlarm(reminder2)
      dao.updateOrInsertAlarm(reminder3)

      val reminders = dao.getAllAlarmDayReminder().getOrAwaitValueAndroidTest()
      Truth.assertThat(reminders).isEqualTo(remindersList)
   }

   @Test
   fun updateReminderRainDaysWhereKeyIsDate_returnTrue() = runBlocking {
      val reminder1 = ReminderRainDay(123, "26.05.2021", "London", 7, 30, "Sunny")
      val reminder2 = ReminderRainDay(23, "26.05.2021", "London", 9, 30, "Sunny")
      val reminder3 = ReminderRainDay(2351, "30.05.2021", "London", 8, 30, "Sunny")
      val remindersList = listOf(reminder2,reminder3)

      dao.updateOrInsertAlarm(reminder1)
      dao.updateOrInsertAlarm(reminder2)
      dao.updateOrInsertAlarm(reminder3)

      val reminders = dao.getAllAlarmDayReminder().getOrAwaitValueAndroidTest()
      Truth.assertThat(reminders).isEqualTo(remindersList)
   }

   @Test
   fun  deleteAllRemindersRainDays_returnTrue() = runBlocking {
      val reminder1 = ReminderRainDay(123, "26.05.2021", "London", 7, 30, "Sunny")
      val reminder2 = ReminderRainDay(2351, "30.05.2021", "London", 8, 30, "Sunny")

      dao.updateOrInsertAlarm(reminder1)
      dao.updateOrInsertAlarm(reminder2)
      dao.deleteAllRemindersRainDays()

      val reminders = dao.getAllAlarmDayReminder().getOrAwaitValueAndroidTest()
      Truth.assertThat(reminders).isEqualTo(listOf<ReminderRainDay>())
   }


   @Test
   fun deleteOneReminderFromDatabaseById_returnTrue() = runBlocking {
      val reminder1 = ReminderRainDay(123, "26.05.2021", "London", 7, 30, "Sunny")
      val reminder2 = ReminderRainDay(2351, "30.05.2021", "London", 8, 30, "Sunny")
      val reminder3 = ReminderRainDay(3, "28.05.2021", "London", 8, 30, "Sunny")
      val remindersList = listOf(reminder1, reminder2)

      dao.updateOrInsertAlarm(reminder1)
      dao.updateOrInsertAlarm(reminder2)
      dao.updateOrInsertAlarm(reminder3)
      dao.deleteOneReminderFromDatabase(3)

      val reminders = dao.getAllAlarmDayReminder().getOrAwaitValueAndroidTest()
      Truth.assertThat(reminders).isEqualTo(remindersList)
   }

}

