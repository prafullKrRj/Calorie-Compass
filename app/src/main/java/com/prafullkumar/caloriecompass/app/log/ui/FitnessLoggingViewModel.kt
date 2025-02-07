package com.prafullkumar.caloriecompass.app.log.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.log.data.ExerciseLogEntity
import com.prafullkumar.caloriecompass.app.log.data.FitnessRepository
import com.prafullkumar.caloriecompass.app.log.data.FoodLogEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// ViewModel
class FitnessLoggingViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _foodLogs = MutableStateFlow<List<FoodLogEntity>>(emptyList())
    val foodLogs: StateFlow<List<FoodLogEntity>> = _foodLogs.asStateFlow()

    private val _exerciseLogs = MutableStateFlow<List<ExerciseLogEntity>>(emptyList())
    val exerciseLogs: StateFlow<List<ExerciseLogEntity>> = _exerciseLogs.asStateFlow()

    private val _dailyCaloriesConsumed = MutableStateFlow(0.0)
    val dailyCaloriesConsumed: StateFlow<Double> = _dailyCaloriesConsumed.asStateFlow()

    private val _dailyCaloriesBurned = MutableStateFlow(0.0)
    val dailyCaloriesBurned: StateFlow<Double> = _dailyCaloriesBurned.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allFoodLogs.collect { logs ->
                _foodLogs.value = logs
            }
        }

        viewModelScope.launch {
            repository.allExerciseLogs.collect { logs ->
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

    fun addFoodLog(foodLog: FoodLogEntity) = viewModelScope.launch {
        repository.insertFoodLog(foodLog)
    }

    fun addExerciseLog(exerciseLog: ExerciseLogEntity) = viewModelScope.launch {
        repository.insertExerciseLog(exerciseLog)
    }
}
