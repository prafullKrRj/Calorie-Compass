package com.prafullkumar.caloriecompass.app.home.ui.main

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.R
import com.prafullkumar.caloriecompass.app.home.data.HomeRepositoryImpl
import com.prafullkumar.caloriecompass.app.onBoarding.ui.ActivityLevel
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Goal
import kotlinx.coroutines.launch

/**
 * Enum class representing different macronutrients with their values and descriptions.
 */
enum class MacroNutrients(
    val value: String,
    val description: String
) {
    PROTEIN("Protein", "Protein is essential for muscle growth and repair."),
    FAT("Fat", "Fat is essential for hormone production and brain function."),
    CARBS("Carbs", "Carbs are the body's main source of energy.")
}

/**
 * Enum class representing different parameters with their values, descriptions, and associated drawable resources.
 */
enum class Parameters(
    val value: String,
    val description: String,
    @DrawableRes val image: Int
) {
    BMI(
        "BMI",
        image = R.drawable.bmi,
        description = "Measures body fat based on height and weight."
    ),
    BMR(
        "BMR",
        image = R.drawable.bmr,
        description = "Calories burned at rest for basic body functions."
    ),
    RMR(
        "RMR",
        image = R.drawable.rmr,
        description = "Energy required to maintain vital bodily functions."
    ),
    IBW(
        "IBW",
        image = R.drawable.bmi,
        description = "Ideal body weight based on height and gender."
    )
}

/**
 * Data class representing the state of the Home screen.
 *
 * @property parameters A map of parameters and their values.
 * @property activityLevel The user's activity level.
 * @property tdee The total daily energy expenditure.
 * @property goal The user's goal.
 * @property macroNutrients A map of macronutrients and their values.
 * @property loading A flag indicating if data is being loaded.
 * @property error An error message if any error occurs.
 */
data class HomeState(
    val parameters: Map<Parameters, String> = emptyMap(),
    val activityLevel: ActivityLevel? = null,
    val tdee: Double = 0.0,
    val goal: Goal? = null,
    val macroNutrients: Map<MacroNutrients, Double> = emptyMap(),
    val loading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Home screen.
 *
 * @property repository The repository to fetch data from.
 */
class HomeViewModel(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getData()
    }

    /**
     * Fetches data from the repository and updates the state.
     */
    fun getData() {
        viewModelScope.launch {
            state = state.copy(parameters = emptyMap(), error = null, loading = true)
            val data = repository.getTdeeData()
            Log.d("HomeViewModel", "getData: $data")
            state = state.copy(
                loading = false,
                error = null,
                tdee = data.tdee,
                parameters = mapOf(
                    Parameters.BMI to data.bmi.toString(),
                    Parameters.BMR to data.bmr.toString(),
                    Parameters.RMR to data.rmr.toString(),
                    Parameters.IBW to data.ibw.toString()
                ),
                macroNutrients = mapOf(
                    MacroNutrients.PROTEIN to data.protein,
                    MacroNutrients.FAT to data.fat,
                    MacroNutrients.CARBS to data.carbs
                ),
                goal = data.goal,
                activityLevel = data.activityLevel
            )
        }
    }
}