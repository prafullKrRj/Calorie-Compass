package com.prafullkumar.caloriecompass.app.log.data.repository

import com.prafullkumar.caloriecompass.app.log.data.local.LoggingDao
import com.prafullkumar.caloriecompass.app.log.data.mappers.ExerciseLogMapper
import com.prafullkumar.caloriecompass.app.log.data.mappers.FoodLogMapper
import com.prafullkumar.caloriecompass.app.log.domain.model.ExerciseLog
import com.prafullkumar.caloriecompass.app.log.domain.model.FoodLog
import com.prafullkumar.caloriecompass.app.log.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

// Repository
class FitnessRepositoryImpl(private val loggingDao: LoggingDao) : FitnessRepository {
    private val foodMapper = FoodLogMapper()
    private val exerciseMapper = ExerciseLogMapper()
    override val allFoodLogs: Flow<List<FoodLog>> = loggingDao.getAllFoodLogs().map { list ->
        list.map { foodMapper.mapToDomain(it) }
    }
    override val allExerciseLogs: Flow<List<ExerciseLog>> =
        loggingDao.getAllExerciseLogs().map { list ->
            list.map { exerciseMapper.mapToDomain(it) }
        }

    override val lastFiveMeals: Flow<List<FoodLog>> = loggingDao.getLastFiveMeals().map { list ->
        list.map { foodMapper.mapToDomain(it) }
    }
    override val lastFiveExercises: Flow<List<ExerciseLog>> =
        loggingDao.getLastFiveExercises().map { list ->
            list.map { exerciseMapper.mapToDomain(it) }
        }

    override suspend fun insertFoodLog(foodLog: FoodLog) {
        loggingDao.insertFoodLog(foodMapper.mapToEntity(foodLog.copy(id = 0)))
    }

    override suspend fun insertExerciseLog(exerciseLog: ExerciseLog) {
        loggingDao.insertExerciseLog(exerciseMapper.mapToEntity(exerciseLog.copy(id = 0)))
    }

    override fun getTotalCaloriesForDay(date: Long): Flow<Double> {
        val startOfDay = date.startOfDay()
        val endOfDay = date.endOfDay()
        return loggingDao.getTotalCaloriesForDay(startOfDay, endOfDay)
    }

    override fun getTotalCaloriesBurnedForDay(date: Long): Flow<Double> {
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
