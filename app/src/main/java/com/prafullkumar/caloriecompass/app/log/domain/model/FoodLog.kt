package com.prafullkumar.caloriecompass.app.log.domain.model

import com.prafullkumar.caloriecompass.app.log.MealType

data class FoodLog(
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val mealType: MealType,
    val foodName: String,
    val calories: Double,
    val protein: Double?,
    val carbs: Double?,
    val fats: Double?
)
