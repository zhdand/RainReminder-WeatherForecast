package com.example.rainreminderweatherforecast.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.rainreminderweatherforecast.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

      setSupportActionBar(findViewById(R.id.toolbar))
      initBottomNavigationWithNavController()
   }

   private fun initBottomNavigationWithNavController() {
      val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

      val navController = findNavController(R.id.nav_host_fragment)

      setupActionBarWithNavController(
         navController,
         AppBarConfiguration(
            setOf(
               R.id.CurrentFragment,
               R.id.FutureFragment,
               R.id.ReminderRainFragment,
               R.id.SettingsFragment
            )
         )
      )
      bottomNavigation.setupWithNavController(navController)
   }
}