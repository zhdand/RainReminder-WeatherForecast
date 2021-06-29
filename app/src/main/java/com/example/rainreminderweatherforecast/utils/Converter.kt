package com.example.rainreminderweatherforecast.utils

import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converter {

   companion object {

      fun convertDateTimeToMilliseconds(
         reminderRainDay: ReminderRainDay
      ): Long {
         var dateInMilliseconds: Long = 0
         val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
         val dateString =
            "${reminderRainDay.date} ${reminderRainDay.hour}:${reminderRainDay.minute}"
         try {
            val date = dateFormat.parse(dateString)
            date?.let {
               dateInMilliseconds = it.time
            }
         } catch (e: ParseException) {
            e.printStackTrace()
         }
         return dateInMilliseconds
      }
   }
}