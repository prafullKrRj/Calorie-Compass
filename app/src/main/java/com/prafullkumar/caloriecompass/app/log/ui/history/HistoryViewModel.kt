package com.prafullkumar.caloriecompass.app.log.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.log.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    private val repository: FitnessRepository
) : ViewModel() {

    val allFoodLogs = repository.allFoodLogs.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val allExerciseLogs = repository.allExerciseLogs.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}