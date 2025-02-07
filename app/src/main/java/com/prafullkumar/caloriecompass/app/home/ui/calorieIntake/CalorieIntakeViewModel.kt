package com.prafullkumar.caloriecompass.app.home.ui.calorieIntake

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.home.domain.model.TDEEData
import com.prafullkumar.caloriecompass.app.home.domain.repository.HomeRepository
import kotlinx.coroutines.launch

/**
 * Enum class representing the goal for calorie intake.
 *
 * @property value The string representation of the goal.
 */
enum class IntakeFor(
    val value: String
) {
    LOSE_WEIGHT("Lose Weight"), GAIN_WEIGHT("Gain Weight")
}

/**
 * Enum class representing the level of calorie intake.
 */
enum class IntakeLevel {
    MILD, MODERATE, EXTREME
}

/**
 * Data class representing the calories per day for a specific intake level.
 *
 * @property calories The number of calories.
 * @property percentage The percentage of TDEE.
 * @property lossPerWeek The weight loss or gain per week in kg.
 */
data class IntakeCaloriesPerDay(
    val calories: Int,
    val percentage: Int,
    val lossPerWeek: Float
)

/**
 * Data class representing the UI state for the Calorie Intake screen.
 *
 * @property data The map of intake data.
 * @property loading The loading state.
 * @property error The error message.
 */
data class CalorieIntakeUIState(
    val data: Map<IntakeFor, Map<IntakeLevel, IntakeCaloriesPerDay>> = emptyMap(),
    val loading: Boolean = false,
    val error: String = ""
)

/**
 * ViewModel for the Calorie Intake screen.
 *
 * @property repository The repository to fetch TDEE data.
 */
class CalorieIntakeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    var uiState by mutableStateOf(CalorieIntakeUIState())

    init {
        getData()
    }

    /**
     * Fetches the TDEE data and updates the UI state.
     */
    fun getData() {
        viewModelScope.launch {
            uiState = CalorieIntakeUIState(loading = true, error = "", data = emptyMap())
            try {
                val tdee = repository.getTdeeData()
                uiState =
                    CalorieIntakeUIState(data = computeData(tdee), loading = false, error = "")
            } catch (e: Exception) {
                uiState = CalorieIntakeUIState(
                    data = emptyMap(),
                    loading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    /**
     * Computes the intake data based on the TDEE value.
     *
     * @param tdee The TDEE data.
     * @return The map of intake data.
     */
    private fun computeData(tdee: TDEEData): Map<IntakeFor, Map<IntakeLevel, IntakeCaloriesPerDay>> {
        val tdeeValue = tdee.tdee

        return mapOf(
            IntakeFor.LOSE_WEIGHT to mapOf(
                IntakeLevel.MILD to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 0.90).toInt(),
                    percentage = 90,
                    lossPerWeek = 0.25f
                ),
                IntakeLevel.MODERATE to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 0.80).toInt(),
                    percentage = 80,
                    lossPerWeek = 0.5f
                ),
                IntakeLevel.EXTREME to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 0.61).toInt(),
                    percentage = 61,
                    lossPerWeek = 1.0f
                )
            ),
            IntakeFor.GAIN_WEIGHT to mapOf(
                IntakeLevel.MILD to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 1.10).toInt(),
                    percentage = 110,
                    lossPerWeek = 0.25f // In this case, gain per week
                ),
                IntakeLevel.MODERATE to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 1.20).toInt(),
                    percentage = 120,
                    lossPerWeek = 0.5f
                ),
                IntakeLevel.EXTREME to IntakeCaloriesPerDay(
                    calories = (tdeeValue * 1.39).toInt(),
                    percentage = 139,
                    lossPerWeek = 1.0f
                )
            )
        )
    }
}