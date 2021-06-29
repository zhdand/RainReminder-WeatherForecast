package com.example.rainreminderweatherforecast.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.rainreminderweatherforecast.MyApp
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.repository.db.WeatherDatabase
import com.example.rainreminderweatherforecast.ui.stop_reminder.StopReminderActivity
import com.example.rainreminderweatherforecast.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.example.rainreminderweatherforecast.utils.Constants.NOTIFICATION_ID
import kotlinx.coroutines.*
import javax.inject.Inject

class AlarmService : Service() {

   private lateinit var mediaPlayer: MediaPlayer
   private lateinit var vibrator: Vibrator

   @Inject
   lateinit var weatherDatabase: WeatherDatabase

   override fun onCreate() {
      super.onCreate()

      inject()

      mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

      vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
   }

   private fun inject() {
      (application as MyApp).getMyAppComponent().inject(this)
   }

   override fun onBind(intent: Intent?): IBinder? {
      return null
   }

   override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

      mediaPlayer.isLooping = true
      mediaPlayer.start()

      startVibrate()

      val intentStopReminderActivity = Intent(this, StopReminderActivity::class.java)

      val pendingIntent = PendingIntent.getActivity(this, 0, intentStopReminderActivity, 0)

      val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
         .setAutoCancel(true)
         .setSmallIcon(R.drawable.ic_alarm)
         .setContentTitle(getString(R.string.notification_title))
         .setContentText(getString(R.string.pref_message_by_default_take_umbrella))
         .setPriority(NotificationCompat.PRIORITY_HIGH)
         .setCategory(NotificationCompat.CATEGORY_ALARM)
         .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
         notification.setFullScreenIntent(pendingIntent, true)
      } else {
         notification.setContentIntent(pendingIntent)
         intentStopReminderActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         startActivity(intentStopReminderActivity)
      }

      startForeground(NOTIFICATION_ID, notification.build())

      CoroutineScope(Dispatchers.IO).launch {
         val remindersRainDaysDao = weatherDatabase.getReminderRainDaysDao()

         remindersRainDaysDao.deleteOneReminderFromDatabase(
            intent?.getIntExtra(
               "EXTRA_ALARM_ID",
               0
            ) ?: 0
         )
      }

      return START_STICKY
   }

   private fun startVibrate() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
      } else {
         @Suppress("DEPRECATION")
         vibrator.vibrate(2000)
      }
   }

   override fun onDestroy() {
      mediaPlayer.release()
      super.onDestroy()
   }

}