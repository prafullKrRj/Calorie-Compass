package com.prafullkumar.caloriecompass.app.home.ui.macroNutrient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.home.data.HomeRepositoryImpl
import kotlinx.coroutines.launch

/**
 * Data class representing macronutrient values.
 *
 * @property protein The amount of protein in grams.
 * @property carbs The amount of carbohydrates in grams.
 * @property fat The amount of fat in grams.
 */
data class MacroNutrient(
    val protein: Float,
    val carbs: Float,
    val fat: Float
)

/**
 * Enum class representing different phases of a diet.
 *
 * @property value The string representation of the phase.
 */
enum class Phase(val value: String) {
    MAINTAINING("Maintain"),
    CUTTING("Cutting"),
    BULKING("Bulking")
}

/**
 * Enum class representing different levels of carbohydrate intake.
 */
enum class Carbs {
    LOW, MEDIUM, HIGH
}

/**
 * Data class representing the macronutrient requirements for a specific phase.
 *
 * @property phase The phase of the diet.
 * @property requirements A map of carbohydrate levels to macronutrient values.
 */
data class PhaseData(
    val phase: Phase = Phase.BULKING,
    val requirements: Map<Carbs, MacroNutrient>
)

/**
 * Data class representing the state of macronutrient data.
 *
 * @property data A map of phases to their corresponding phase data.
 * @property loading A boolean indicating if the data is currently being loaded.
 * @property error A string containing any error message.
 */
data class MacroNutrientState(
    val data: Map<Phase, PhaseData> = emptyMap(),
    val loading: Boolean = false,
    val error: String = ""
)

/**
 * ViewModel class for managing macronutrient data.
 *
 * @property repository The repository used to fetch TDEE data.
 */
class MacroNutrientViewModel(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    // State variable to hold the macronutrient data.
    var macroNutrientState by mutableStateOf<MacroNutrientState?>(null)

    // Initialize the ViewModel by fetching data.
    init {
        getData()
    }

    /**
     * Fetches the macronutrient data from the repository.
     */
    fun getData() {
        viewModelScope.launch {
            try {
                val tdee = repository.getTdeeData() // Assume this returns the TDEE value

                val maintaining = PhaseData(
                    Phase.MAINTAINING,
                    mapOf(
                        Carbs.LOW to calculateMacros(tdee.tdee.toFloat(), 0.20f, 0.40f, 0.40f),
                        Carbs.MEDIUM to calculateMacros(tdee.tdee.toFloat(), 0.40f, 0.30f, 0.30f),
                        Carbs.HIGH to calculateMacros(tdee.tdee.toFloat(), 0.55f, 0.25f, 0.20f)
                    )
                )

                val cutting = PhaseData(
                    Phase.CUTTING,
                    mapOf(
                        Carbs.LOW to calculateMacros(
                            tdee.tdee.toFloat() * 0.85f,
                            0.20f,
                            0.40f,
                            0.40f
                        ),
                        Carbs.MEDIUM to calculateMacros(
                            tdee.tdee.toFloat() * 0.85f,
                            0.40f,
                            0.30f,
                            0.30f
                        ),
                        Carbs.HIGH to calculateMacros(
                            tdee.tdee.toFloat() * 0.85f,
                            0.55f,
                            0.25f,
                            0.20f
                        )
                    )
                )

                val bulking = PhaseData(
                    Phase.BULKING,
                    mapOf(
                        Carbs.LOW to calculateMacros(
                            tdee.tdee.toFloat() * 1.15f,
                            0.20f,
                            0.40f,
                            0.40f
                        ),
                        Carbs.MEDIUM to calculateMacros(
                            tdee.tdee.toFloat() * 1.15f,
                            0.40f,
                            0.30f,
                            0.30f
                        ),
                        Carbs.HIGH to calculateMacros(
                            tdee.tdee.toFloat() * 1.15f,
                            0.55f,
                            0.25f,
                            0.20f
                        )
                    )
                )
                macroNutrientState = MacroNutrientState(
                    data = mapOf(
                        Phase.MAINTAINING to maintaining,
                        Phase.CUTTING to cutting,
                        Phase.BULKING to bulking
                    ),
                    loading = false
                )
            } catch (e: Exception) {
                macroNutrientState = MacroNutrientState(
                    loading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }

    /**
     * Calculates the macronutrient values based on the given ratios.
     *
     * @param calories The total calories.
     * @param carbRatio The ratio of carbohydrates.
     * @param proteinRatio The ratio of protein.
     * @param fatRatio The ratio of fat.
     * @return A MacroNutrient object containing the calculated values.
     */
    private fun calculateMacros(
        calories: Float,
        carbRatio: Float,
        proteinRatio: Float,
        fatRatio: Float
    ): MacroNutrient {
        return MacroNutrient(
            protein = (calories * proteinRatio) / 4,
            carbs = (calories * carbRatio) / 4,
            fat = (calories * fatRatio) / 9
        )
    }
}