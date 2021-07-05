package com.example.rainreminderweatherforecast.ui.stop_reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rainreminderweatherforecast.R

class StopReminderActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.stop_reminder_activity)

      if (savedInstanceState == null) {
         supportFragmentManager.beginTransaction()
            .replace(R.id.container, StopReminderFragment())
            .commitNow()
      }
   }
}