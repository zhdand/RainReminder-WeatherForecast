package com.example.rainreminderweatherforecast.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
   tableName = "future_weather"
)
@Parcelize
data class FutureWeather(
   @PrimaryKey(autoGenerate = false)
   val id:Int,
   val date: String,
   @ColumnInfo(name = "city_name")
   val cityName: String,
   val temperature: Double,
   val wind: Double,
   val precipitation: Double,
   val image: String,
   val description: String

) : Parcelable
