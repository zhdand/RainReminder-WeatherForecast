package com.example.rainreminderweatherforecast.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.rainreminderweatherforecast.R

class SettingsFragment : PreferenceFragmentCompat(),
   SharedPreferences.OnSharedPreferenceChangeListener {

   override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
      setPreferencesFromResource(R.xml.root_preferences, rootKey)
   }

   override fun onResume() {
      super.onResume()
      preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
      findPreference<EditTextPreference>("reminder_time_by_default")?.let {
         it.summary = it.text
      }
   }

   override fun onPause() {
      super.onPause()
      preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
   }

   override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
      when (key) {
         "use_current_location" -> {
            findPreference<SwitchPreferenceCompat>("use_custom_location")?.isChecked =
               findPreference<SwitchPreferenceCompat>("use_current_location")?.isChecked != true
         }
         "use_custom_location" -> {
            val preference = findPreference<SwitchPreferenceCompat>("use_custom_location")
            findPreference<SwitchPreferenceCompat>("use_current_location")?.isChecked =
               preference?.isChecked != true
         }
         "reminder_time_by_default" -> {
            val reminderPreference =
               findPreference<EditTextPreference>("reminder_time_by_default")
            if (!isValidTime(reminderPreference?.text)) {
               Toast.makeText(
                  requireContext(),
                  getString(R.string.pref_message_enter_valid_time),
                  Toast.LENGTH_SHORT
               ).show()
               reminderPreference?.summary = getString(R.string.pref_message_summary_set_valid_time)
               reminderPreference?.text = ""
            } else {
               reminderPreference?.summary = reminderPreference?.text
            }
         }
      }
   }

   /**
    * Checks if the entered reminder time is valid or not.
    * @param reminderPreference - the entered time for the alarm in the settings.
    */
   private fun isValidTime(reminderPreference: String?): Boolean {
      try {
         reminderPreference?.let { stringTime ->
            when {
               stringTime.isEmpty() -> {
                  return false
               }
               stringTime.length != 5 -> {
                  return false
               }
               stringTime.take(2).toInt() > 23 -> {
                  return false
               }
               stringTime.takeLast(2).toInt() > 59 -> {
                  return false
               }
               stringTime.indexOf(char = ':', startIndex = 0, ignoreCase = false) != 2 -> {
                  return false
               }
               else -> return true
            }
         }
      } catch (e: Exception) {
         e.printStackTrace()
         return false
      }
      return true
   }
}
