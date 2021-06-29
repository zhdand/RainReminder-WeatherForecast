package com.example.rainreminderweatherforecast.repository.network.mapper

import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.repository.network.response.current_weather.CurrentWeatherDataDto
import com.example.rainreminderweatherforecast.repository.network.response.future_weather.ResponseFutureWeather
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

object Mapper {

      fun mapCurrentWeatherDataDtoToCurrentWeather(currentWeatherDto: CurrentWeatherDataDto): CurrentWeather {
         return CurrentWeather(
            date = currentWeatherDto.datetime.dropLast(3),
            cityName = currentWeatherDto.cityName,
            temperature = currentWeatherDto.temperature,
            wind = roundDouble(currentWeatherDto.windSpeed),
            precipitation = roundDouble(currentWeatherDto.precipitation),
            image = currentWeatherDto.currentWeather.icon,
            description = currentWeatherDto.currentWeather.description,
            created = SimpleDateFormat(
               "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH
            ).format(Date())
         )
      }

      fun mapResponseFutureWeatherToFutureWeather(responseFutureWeather: ResponseFutureWeather): MutableList<FutureWeather> {
         var id = 1
         val futureWeather = mutableListOf<FutureWeather>()
         responseFutureWeather.data.forEach { futureWeatherDataDto ->
            futureWeather.add(
               FutureWeather(
                  id =  id ++,//0, //
                  date = futureWeatherDataDto.datetime,
                  cityName = responseFutureWeather.cityName,
                  temperature = futureWeatherDataDto.temperature,
                  wind = roundDouble(futureWeatherDataDto.windSpeed),
                  precipitation = roundDouble(futureWeatherDataDto.precipitation),
                  image = futureWeatherDataDto.futureWeather.icon,
                  description = futureWeatherDataDto.futureWeather.description
               )
            )
         }
         return futureWeather
      }

      private fun roundDouble(precipitation: Double): Double {
         return precipitation.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
      }

}
