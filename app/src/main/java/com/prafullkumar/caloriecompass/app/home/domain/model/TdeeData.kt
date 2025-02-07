package com.prafullkumar.caloriecompass.app.home.domain.model

import com.prafullkumar.caloriecompass.app.onBoarding.ui.ActivityLevel
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Goal

/**
 * Data class representing Total Daily Energy Expenditure (TDEE) data.
 *
 * @property tdee The total daily energy expenditure.
 * @property bmr The basal metabolic rate.
 * @property bmi The body mass index.
 * @property rmr The resting metabolic rate.
 * @property ibw The ideal body weight.
 * @property goal The user's goal (e.g., lose weight, gain weight, maintain weight).
 * @property activityLevel The user's activity level.
 * @property protein The recommended daily protein intake.
 * @property fat The recommended daily fat intake.
 * @property carbs The recommended daily carbohydrate intake.
 */
data class TDEEData(
    val tdee: Double,
    val bmr: Double,
    val bmi: Double,
    val rmr: Double,
    val ibw: Double,
    val goal: Goal,
    val activityLevel: ActivityLevel,
    val protein: Double,
    val fat: Double,
    val carbs: Double
)