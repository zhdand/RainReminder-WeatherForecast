package com.example.rainreminderweatherforecast.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = ReminderRainDay.TABLE_NAME)
@Parcelize
data class ReminderRainDay(
    val id: Int,
    @PrimaryKey
    val date: String,
    val city: String,
    val hour: Int,
    val minute: Int,
    val description: String
): Parcelable {

    companion object{
        const val TABLE_NAME = "reminder_rain_day"
    }
}