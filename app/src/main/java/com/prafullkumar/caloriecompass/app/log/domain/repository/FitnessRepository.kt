package com.prafullkumar.caloriecompass.app.log.domain.repository


import com.prafullkumar.caloriecompass.app.log.domain.model.ExerciseLog
import com.prafullkumar.caloriecompass.app.log.domain.model.FoodLog
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    val allFoodLogs: Flow<List<FoodLog>>
    val allExerciseLogs: Flow<List<ExerciseLog>>

    val lastFiveMeals: Flow<List<FoodLog>>
    val lastFiveExercises: Flow<List<ExerciseLog>>

    suspend fun insertFoodLog(foodLog: FoodLog)
    suspend fun insertExerciseLog(exerciseLog: ExerciseLog)

    fun getTotalCaloriesForDay(date: Long): Flow<Double>
    fun getTotalCaloriesBurnedForDay(date: Long): Flow<Double>
}