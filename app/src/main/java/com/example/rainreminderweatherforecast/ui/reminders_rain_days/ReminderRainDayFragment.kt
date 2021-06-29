package com.example.rainreminderweatherforecast.ui.reminders_rain_days

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.rainreminderweatherforecast.MyApp
import com.example.rainreminderweatherforecast.R
import com.google.android.material.button.MaterialButton
import javax.inject.Inject

/**
 * A fragment representing a list of Reminders.
 */
class ReminderRainDayFragment : Fragment(R.layout.fragment_list_reminder_rain_day) {

   @Inject
   lateinit var viewModelProvidersFactory: ViewModelProvider.Factory
   lateinit var viewModelReminder: ReminderRainDayViewModel

   private lateinit var recyclerViewReminderRainDay: RecyclerView
   lateinit var adapterRainDay: ReminderDayRecyclerViewAdapter
   private lateinit var buttonClearAll: MaterialButton

   override fun onAttach(context: Context) {
      super.onAttach(context)

      inject()
   }

   private fun inject() {
      val appComponent = activity?.run { (application as MyApp).getMyAppComponent() }
         ?: throw Exception("AppComponent is null")

      appComponent.reminderRainDaysSubcomponent().create().inject(this)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      viewModelReminder =
         ViewModelProvider(this, viewModelProvidersFactory)[ReminderRainDayViewModel::class.java]

      recyclerViewReminderRainDay = view.findViewById(R.id.recycler_view_reminder_rain_day)
      buttonClearAll = view.findViewById(R.id.button_clear_all_reminders)

      initRecyclerView()

      initObservers()

      initButtonClickListener()
   }

   private fun initRecyclerView() {
      adapterRainDay = ReminderDayRecyclerViewAdapter()

      recyclerViewReminderRainDay.apply {
         layoutManager = LinearLayoutManager(requireContext())
         val itemTouchHelper = ItemTouchHelper(object : ItemSwipeDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val date =
                  viewHolder.itemView.findViewById<TextView>(R.id.text_view_date_reminder).text.toString()
               val reminderId = getReminderId(date)
               viewModelReminder.cancelOnReminderRainDay(reminderId)
               adapterRainDay.removeAt(viewHolder.adapterPosition)
            }

            private fun getReminderId(date: String): Int {
               return viewModelReminder.reminders.value?.let { remindersList ->
                  remindersList.filter { it.date == date }[0].id
               } ?: 0
            }

         })
         itemTouchHelper.attachToRecyclerView(recyclerViewReminderRainDay)
         recyclerViewReminderRainDay.adapter = adapterRainDay
      }
   }

   private fun initObservers() {
      viewModelReminder.reminders.observe(viewLifecycleOwner, { reminders ->
         adapterRainDay.data = reminders.toMutableList()
      })
   }

   private fun initButtonClickListener() {
      buttonClearAll.setOnClickListener {
         viewModelReminder.clearAllRemindersRainDays()
      }
   }

   abstract inner class ItemSwipeDelete : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
      override fun onMove(
         recyclerView: RecyclerView,
         viewHolder: RecyclerView.ViewHolder,
         target: RecyclerView.ViewHolder
      ): Boolean {
         return false
      }
   }
}

