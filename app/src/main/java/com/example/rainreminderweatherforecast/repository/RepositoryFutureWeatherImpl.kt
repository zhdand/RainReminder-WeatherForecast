package com.example.rainreminderweatherforecast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.domain.IRepositoryFutureWeather
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.repository.db.IFutureWeatherDao
import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService
import com.example.rainreminderweatherforecast.repository.network.mapper.Mapper
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.IsNetworkAvailableProvider
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.repository.providers.location.LocationProviderImpl
import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider
import com.example.rainreminderweatherforecast.utils.Constants
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RepositoryFutureWeatherImpl
@Inject constructor(
   private val futureWeatherDaoDB: IFutureWeatherDao,
   private val apiService: IWeatherAPIService,
   private val isNetworkAvailableProvider: IsNetworkAvailableProvider,
   private val locationProvider: LocationProviderImpl,
   private val preferencesProvider: IPreferencesProvider
) : IRepositoryFutureWeather {

   private var _networkResponseResult = MutableLiveData<ResultWrapper>()
   override val networkResponseResult: LiveData<ResultWrapper>
      get() = _networkResponseResult

   override fun isNetworkAvailable(): LiveData<ResultNetworkAvailableState> {
      return isNetworkAvailableProvider.getIsNetworkAvailable()
   }

   /**
    * Performs a call to the web, in order to get future weather,
    * depending on whether the device's current location is enabled or the location is set manually.
    */
   override suspend fun getFutureWeatherFromNetwork() {
      if (preferencesProvider.isUsingCurrentDeviceLocation()) {
         locationProvider.currentLocationLatLng.collect { locationLatLngModel ->
            if (locationLatLngModel != null) {
               fetchFutureWeatherByCoordinates(
                  locationLatLngModel.latitude,
                  locationLatLngModel.longitude,
                  "en"
               )
            } else {
               locationProvider.getLastDeviceLocation()
            }
         }

      } else if (preferencesProvider.isUsingCustomLocation()) {
         fetchFutureWeatherByCityName(
            preferencesProvider.getCustomLocationCityName(),
            preferencesProvider.getCustomLocationCountry(),
            "en"
         )
      }
   }

   /**
    * Fetches weather forecast by coordinates
    * @param latitude
    * @param longitude
    * @param language specific language
    * and than saves it in the database
    */
   private suspend fun fetchFutureWeatherByCoordinates(
      latitude: Double,
      longitude: Double,
      language: String
   ) {

      try {
         _networkResponseResult.postValue(ResultWrapper.Loading)
         val response = apiService.getFutureWeather(latitude, longitude, language)
         if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
               _networkResponseResult.postValue(ResultWrapper.Success)
               val futureWeather = Mapper.mapResponseFutureWeatherToFutureWeather(body)
               saveFutureWeatherToDatabase(futureWeather)
            } else {
               _networkResponseResult.postValue(
                  ResultWrapper.Error(R.string.message_network_enter_correct_city_name)
               )
            }
         } else {
            _networkResponseResult.postValue(
               ResultWrapper.Error(R.string.message_repository_network_error)
            )
         }
      } catch (e: Exception) {
         e.printStackTrace()
         switchNetworkResponseResultToEmpty()
      }
   }

   private suspend fun switchNetworkResponseResultToEmpty() {
      delay(200)
      _networkResponseResult.postValue(ResultWrapper.Empty)
   }

   /**
    * Fetches weather forecast by city and country name
    * @param city City name
    * @param country Country name
    * @param language Specific language
    * and than saves it in the database
    */
   private suspend fun fetchFutureWeatherByCityName(
      city: String?,
      country: String?,
      language: String
   ) {
      try {
         _networkResponseResult.postValue(ResultWrapper.Loading)
         val response = apiService.getFutureWeather(city, country, language)
         if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
               _networkResponseResult.postValue(ResultWrapper.Success)
               val futureWeather = Mapper.mapResponseFutureWeatherToFutureWeather(body)
               saveFutureWeatherToDatabase(futureWeather)
            } else {
               _networkResponseResult.postValue(
                  ResultWrapper.Error(R.string.message_network_enter_correct_city_name)
               )
            }
         } else {
            _networkResponseResult.postValue(
               ResultWrapper.Error(R.string.message_repository_network_error)
            )
         }
         switchNetworkResponseResultToEmpty()
      } catch (e: Exception) {
         e.printStackTrace()
         switchNetworkResponseResultToEmpty()
      }
   }

   /**
    * Gets weather forecast from the database.
    * @return Returns LiveData with List<FutureWeather>
    */
   override fun getFutureWeatherFromDatabase(): LiveData<List<FutureWeather>> {
      return futureWeatherDaoDB.getFutureWeather()
   }

   /**
    * Saves the weather forecast, retrieved from the internet, in a database.
    */
   override suspend fun saveFutureWeatherToDatabase(futureWeather: List<FutureWeather>) {
      futureWeatherDaoDB.updateOrInsertFutureWeather(futureWeather)
   }

}