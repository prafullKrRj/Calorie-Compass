package com.prafullkumar.caloriecompass.common.data

import android.content.Context

/**
 * A manager class for handling shared preferences related to the Calorie Compass app.
 *
 * @property context The context used to access the shared preferences.
 */
class SharedPrefManager(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "calorie_compass"
        private const val ONBOARDED = "onboarded"
    }

    /**
     * Retrieves the onboarded status from shared preferences.
     *
     * @return True if the user has been onboarded, false otherwise.
     */
    fun getOnBoarded(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDED, false)
    }

    /**
     * Sets the onboarded status to true in shared preferences.
     */
    fun setOnBoarded() {
        sharedPreferences.edit().putBoolean(ONBOARDED, true).apply()
    }
}