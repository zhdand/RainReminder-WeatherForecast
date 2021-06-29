package com.example.rainreminderweatherforecast.ui.reminders_rain_days

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay

/**
 * RecyclerView adapter for reminders rain days list
 */
class ReminderDayRecyclerViewAdapter(

) : RecyclerView.Adapter<ReminderDayRecyclerViewAdapter.ViewHolder>() {

   var data = mutableListOf<ReminderRainDay>()
      set(value) {
         field = value
         notifyDataSetChanged()
      }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
         .inflate(R.layout.item_list_reminder_rain_day, parent, false)
      return ViewHolder(view)
   }

   fun removeAt(position: Int){
      data.removeAt(position)
      notifyItemRemoved(position)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = data[position]
      holder.textViewDate.text = item.date
      holder.textViewCity.text = item.city
      val timeString = "${
         if (item.hour < 10) "0" + item.hour
         else item.hour
      }:${
         if (item.minute < 10) "0" + item.minute
         else item.minute
      }"
      holder.textViewTime.text = timeString
      holder.textViewDescription.text = item.description
   }

   override fun getItemCount(): Int = data.size

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val textViewDate: TextView = view.findViewById(R.id.text_view_date_reminder)
      val textViewCity: TextView = view.findViewById(R.id.text_view_city_name)
      val textViewTime: TextView = view.findViewById(R.id.text_view_time_reminder)
      val textViewDescription: TextView = view.findViewById(R.id.text_view_description_reminder)
   }
}