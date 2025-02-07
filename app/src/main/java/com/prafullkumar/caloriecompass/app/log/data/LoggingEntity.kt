package com.prafullkumar.caloriecompass.app.log.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prafullkumar.caloriecompass.app.log.MealType

@Entity(tableName = "food_log")
data class FoodLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val mealType: MealType,
    val foodName: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fats: Double
)