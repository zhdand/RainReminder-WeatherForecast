package com.example.rainreminderweatherforecast

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.example.rainreminderweatherforecast.di.app.AppComponent
import com.example.rainreminderweatherforecast.di.app.DaggerAppComponent
import com.example.rainreminderweatherforecast.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.example.rainreminderweatherforecast.utils.Constants.NOTIFICATION_CHANNEL_NAME
import javax.inject.Inject

class MyApp @Inject constructor() : Application() {

   private lateinit var appComponent: AppComponent

   fun getMyAppComponent(): AppComponent {
      return appComponent
   }

   override fun onCreate() {
      super.onCreate()

      initDagger()

      createNotificationChannel()
   }

   private fun initDagger() {
      appComponent = DaggerAppComponent.builder()
         .context(this)
         .app(this)
         .build()
   }

   private fun createNotificationChannel() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
         )

         val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

         notificationChannel.apply {
            enableVibration(true)
            enableLights(true)
            lightColor = Color.GREEN
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
         }

         notificationManager.createNotificationChannel(notificationChannel)
      }
   }

}