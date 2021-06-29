package com.example.rainreminderweatherforecast.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.service.AlarmService

class MyAlarmReceiver : BroadcastReceiver() {
   override fun onReceive(context: Context?, intent: Intent?) {

      Toast.makeText(context, context?.getString(R.string.pref_message_by_default_take_umbrella), Toast.LENGTH_LONG).show()

      val intentService = Intent(context, AlarmService::class.java)
      intentService.putExtra("EXTRA_ALARM_ID", intent?.getIntExtra("EXTRA_ALARM_ID", 0))
      if (Build.VERSION.SDK_INT >= 26 ){
         context?.startForegroundService(intentService)
      }else{
         context?.startService(intentService)
      }
   }
}