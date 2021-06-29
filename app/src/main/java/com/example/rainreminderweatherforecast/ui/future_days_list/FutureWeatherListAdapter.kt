package com.example.rainreminderweatherforecast.ui.future_days_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService
import java.text.DecimalFormat

class FutureWeatherListAdapter constructor(
   private val clickListener: OnItemClickListenerRecyclerView
) : RecyclerView.Adapter<FutureWeatherListAdapter.FutureViewHolder>() {

   var data = listOf<FutureWeather>()
      set(value) {
         field = value
         notifyDataSetChanged()
      }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureViewHolder {
      return FutureViewHolder(
         LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_future_weather, parent, false)
      )
   }

   override fun onBindViewHolder(holder: FutureViewHolder, position: Int) {
      holder.bind(data[position])
   }

   override fun getItemCount(): Int {
      return data.size
   }

   inner class FutureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
      View.OnClickListener {

      private val imageIcon: ImageView = itemView.findViewById(R.id.imageViewItemFutureWeather)
      private val textViewDateTemperature: TextView =
         itemView.findViewById(R.id.textViewItemDateTemperature)
      private val textViewPrecipitation: TextView =
         itemView.findViewById(R.id.textViewItemPrecipitation)
      private val textViewDescription: TextView =
         itemView.findViewById(R.id.textViewItemDescription)

      init {
         itemView.setOnClickListener(this)
      }

      fun bind(data: FutureWeather) {
         Glide.with(imageIcon)
            .load("${IWeatherAPIService.URL_ICON}${data.image}.png")
            .into(imageIcon)

         val dateAndTemperature = "${data.date}     ${data.temperature} \u2103"
         textViewDateTemperature.text = dateAndTemperature

         var precipitation = DecimalFormat("0.0").format(data.precipitation)
         precipitation = "precipitation $precipitation mm"
         textViewPrecipitation.text = precipitation

         textViewDescription.text = data.description
      }

      override fun onClick(v: View?) {
         val position = adapterPosition
         if (position != RecyclerView.NO_POSITION) {
            clickListener.onItemClick(position)
         }
      }

   }

   interface OnItemClickListenerRecyclerView {
      fun onItemClick(position: Int)
   }

}