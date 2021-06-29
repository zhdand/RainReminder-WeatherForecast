package com.example.rainreminderweatherforecast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.repository.network.mapper.Mapper
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import com.example.rainreminderweatherforecast.domain.IRepositoryCurrentWeather
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.repository.db.ICurrentWeatherDao
import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.IsNetworkAvailableProvider
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.repository.providers.location.LocationProviderImpl
import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlinx.coroutines.delay

class RepositoryCurrentWeatherImpl
@Inject
constructor(
   private val currentWeatherDaoDB: ICurrentWeatherDao,
   private val apiService: IWeatherAPIService,
   private val isNetworkAvailableProvider: IsNetworkAvailableProvider,
   private val locationProvider: LocationProviderImpl,
   private val preferencesProvider: IPreferencesProvider
) : IRepositoryCurrentWeather {

   private var _networkResponseResult = MutableLiveData<ResultWrapper>()
   override val networkResponseResult: LiveData<ResultWrapper>
      get() = _networkResponseResult

   override fun isNetworkAvailable(): LiveData<ResultNetworkAvailableState> {
      return isNetworkAvailableProvider.getIsNetworkAvailable()
   }

   /**
    * Performs a call to the web, in order to get current weather,
    * depending on whether the device's current location is enabled or the location is set manually.
    */
   override suspend fun getCurrentWeather() {
      if (preferencesProvider.isUsingCurrentDeviceLocation()) {
         locationProvider.currentLocationLatLng.collect { locationLatLngModel ->

            if (locationLatLngModel != null) {
               fetchCurrentWeatherByCoordinates(
                  locationLatLngModel.latitude,
                  locationLatLngModel.longitude,
                  "en"
               )
            } else {
               locationProvider.getLastDeviceLocation()
            }
         }
      } else if (preferencesProvider.isUsingCustomLocation()) {
         fetchCurrentWeatherByCityName(
            preferencesProvider.getCustomLocationCityName(),
            preferencesProvider.getCustomLocationCountry(),
            "en"
         )
      }
   }

   /**
    * Fetches current weather by coordinates
    * @param latitude
    * @param longitude
    * @param language specific language
    * and than saves it in the database
    */
   private suspend fun fetchCurrentWeatherByCoordinates(
      latitude: Double,
      longitude: Double,
      language: String
   ) {
      try {
         _networkResponseResult.postValue(ResultWrapper.Loading)
         val response = apiService.getCurrentWeather(latitude, longitude, language)
         if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
               _networkResponseResult.postValue(ResultWrapper.Success)
               val currentWeather = Mapper.mapCurrentWeatherDataDtoToCurrentWeather(body.data[0])
              saveCurrentWeatherToDatabase(currentWeather)
            } else {
               _networkResponseResult.postValue(
                  ResultWrapper.Error(R.string.message_repository_network_error)
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

   private suspend fun switchNetworkResponseResultToEmpty() {
      delay(200)
      _networkResponseResult.postValue(ResultWrapper.Empty)
   }

   /**
    * Fetches current weather by city and country name
    * @param city City name
    * @param country Country name
    * @param language Specific language
    * and than saves it in the database
    */
   private suspend fun fetchCurrentWeatherByCityName(
      city: String?,
      country: String?,
      language: String
   ) {
      try {
         _networkResponseResult.postValue(ResultWrapper.Loading)
         val response = apiService.getCurrentWeather(city, country, language)
         if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
               _networkResponseResult.postValue(ResultWrapper.Success)
               val currentWeather = Mapper.mapCurrentWeatherDataDtoToCurrentWeather(body.data[0])
               saveCurrentWeatherToDatabase(currentWeather)
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
    * Gets current weather from database.
    * @return Returns LiveData with CurrentWeather
    */
   override fun getCurrentWeatherFromDatabase() =
      currentWeatherDaoDB.getCurrentWeather()

   /**
    * Saves the current weather, retrieved from the internet, in a database.
    */
   override suspend fun saveCurrentWeatherToDatabase(currentWeather: CurrentWeather) {
      currentWeatherDaoDB.updateOrInsertCurrentWeather(currentWeather)
   }

}