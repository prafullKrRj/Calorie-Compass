package com.prafullkumar.caloriecompass.app.log.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.log.domain.model.ExerciseLog
import com.prafullkumar.caloriecompass.app.log.domain.model.FoodLog
import com.prafullkumar.caloriecompass.app.log.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// ViewModel
class FitnessLoggingViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _foodLogs = MutableStateFlow<List<FoodLog>>(emptyList())
    val foodLogs: StateFlow<List<FoodLog>> = _foodLogs.asStateFlow()

    private val _exerciseLogs = MutableStateFlow<List<ExerciseLog>>(emptyList())
    val exerciseLogs: StateFlow<List<ExerciseLog>> = _exerciseLogs.asStateFlow()

    private val _dailyCaloriesConsumed = MutableStateFlow(0.0)
    val dailyCaloriesConsumed: StateFlow<Double> = _dailyCaloriesConsumed.asStateFlow()

    private val _dailyCaloriesBurned = MutableStateFlow(0.0)
    val dailyCaloriesBurned: StateFlow<Double> = _dailyCaloriesBurned.asStateFlow()

    init {
        viewModelScope.launch {
            repository.lastFiveMeals.collect { logs ->
                _foodLogs.value = logs
            }
        }

        viewModelScope.launch {
            repository.lastFiveExercises.collect { logs ->
                _exerciseLogs.value = logs
            }
        }

        viewModelScope.launch {
            repository.getTotalCaloriesForDay(System.currentTimeMillis()).collect { calories ->
                _dailyCaloriesConsumed.value = calories
            }
        }

        viewModelScope.launch {
            repository.getTotalCaloriesBurnedForDay(System.currentTimeMillis())
                .collect { calories ->
                    _dailyCaloriesBurned.value = calories
                }
        }
    }

    fun addFoodLog(foodLog: FoodLog) = viewModelScope.launch {
        repository.insertFoodLog(foodLog)
    }

    fun addExerciseLog(exerciseLog: ExerciseLog) = viewModelScope.launch {
        repository.insertExerciseLog(exerciseLog)
    }
}
