package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.domain.IRepositoryReminders
import javax.inject.Inject

class DeleteOneReminderFromDatabaseUseCase
@Inject constructor(
   private val repository: IRepositoryReminders
) {
   suspend operator fun invoke(id: Int){
      repository.deleteOneReminderFromDatabase(id)
   }
}