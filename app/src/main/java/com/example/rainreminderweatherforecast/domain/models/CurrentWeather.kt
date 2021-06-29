package com.example.rainreminderweatherforecast.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rainreminderweatherforecast.repository.db.WeatherDatabase
import com.example.rainreminderweatherforecast.ui.models.CurrentWeatherUI
import kotlinx.parcelize.Parcelize

@Entity(tableName = WeatherDatabase.TABLE_NAME_CURRENT_WEATHER)
@Parcelize
data class CurrentWeather(

   @PrimaryKey(autoGenerate = false)
   val id: Int = 1,
   val date: String,
   @ColumnInfo(name = "city_name")
   val cityName: String,
   val temperature: Double,
   val wind: Double,
   val precipitation: Double,
   val image: String,
   val created: String,
   val description: String
) : Parcelable {

   fun mapToUI(): CurrentWeatherUI {
      return CurrentWeatherUI(
         cityName = "City: ${this.cityName}",
         temperature = "${this.temperature} ℃",
         wind = "Wind speed: ${this.wind} m/s",
         precipitation = "Precipitation: ${this.precipitation} mm",
         image = this.image,
         description = this.description
      )
   }

}