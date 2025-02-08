package com.prafullkumar.caloriecompass.app.log.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodLog(foodLog: FoodLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseLog(exerciseLog: ExerciseLogEntity)

    @Query("SELECT * FROM food_log ORDER BY date DESC")
    fun getAllFoodLogs(): Flow<List<FoodLogEntity>>

    @Query("SELECT * FROM exercise_log ORDER BY date DESC")
    fun getAllExerciseLogs(): Flow<List<ExerciseLogEntity>>

    @Query("SELECT SUM(calories) FROM food_log WHERE date >= :startOfDay AND date < :endOfDay")
    fun getTotalCaloriesForDay(startOfDay: Long, endOfDay: Long): Flow<Double>

    @Query("SELECT SUM(caloriesBurned) FROM exercise_log WHERE date >= :startOfDay AND date < :endOfDay")
    fun getTotalCaloriesBurnedForDay(startOfDay: Long, endOfDay: Long): Flow<Double>

    @Query("SELECT * FROM food_log ORDER BY date DESC LIMIT 5")
    fun getLastFiveMeals(): Flow<List<FoodLogEntity>>

    @Query("SELECT * FROM exercise_log ORDER BY date DESC LIMIT 5")
    fun getLastFiveExercises(): Flow<List<ExerciseLogEntity>>
}