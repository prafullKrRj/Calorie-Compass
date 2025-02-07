package com.prafullkumar.caloriecompass.app.log.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

// Repository
class FitnessRepository(private val loggingDao: LoggingDao) {
    val allFoodLogs: Flow<List<FoodLogEntity>> = loggingDao.getAllFoodLogs()
    val allExerciseLogs: Flow<List<ExerciseLogEntity>> = loggingDao.getAllExerciseLogs()

    suspend fun insertFoodLog(foodLog: FoodLogEntity) {
        loggingDao.insertFoodLog(foodLog)
    }

    suspend fun insertExerciseLog(exerciseLog: ExerciseLogEntity) {
        loggingDao.insertExerciseLog(exerciseLog)
    }

    fun getTotalCaloriesForDay(date: Long): Flow<Double> {
        val startOfDay = date.startOfDay()
        val endOfDay = date.endOfDay()
        return loggingDao.getTotalCaloriesForDay(startOfDay, endOfDay)
    }

    fun getTotalCaloriesBurnedForDay(date: Long): Flow<Double> {
        val startOfDay = date.startOfDay()
        val endOfDay = date.endOfDay()
        return loggingDao.getTotalCaloriesBurnedForDay(startOfDay, endOfDay)
    }
}

fun Long.startOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Long.endOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}
