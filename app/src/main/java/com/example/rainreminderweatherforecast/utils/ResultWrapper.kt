package com.example.rainreminderweatherforecast.utils

sealed class ResultWrapper(
   val errorMessage: Int? = null
) {
   object Success : ResultWrapper()
   class Error(errorMessage: Int) : ResultWrapper(errorMessage)
   object Loading : ResultWrapper()
   object Empty: ResultWrapper()

}
