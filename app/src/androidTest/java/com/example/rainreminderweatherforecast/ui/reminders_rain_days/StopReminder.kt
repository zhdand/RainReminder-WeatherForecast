package com.example.rainreminderweatherforecast.ui.reminders_rain_days

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.ui.stop_reminder.StopReminderActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StopReminder {

   @get:Rule
   val activityRule = ActivityScenarioRule(StopReminderActivity::class.java)

   @Test
   fun stopReminder_isDisplayed(){
      onView(withId(R.id.button_stop_reminder)).check(matches(isDisplayed()))
   }
}