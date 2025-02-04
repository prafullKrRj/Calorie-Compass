package com.prafullkumar.caloriecompass.data

import android.content.Context

class SharedPrefManager(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "calorie_compass"
        private const val ONBOARDED = "onboarded"
    }

    fun getOnBoarded(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDED, false)
    }

    fun setOnBoarded() {
        sharedPreferences.edit().putBoolean(ONBOARDED, true).apply()
    }
}