package com.prafullkumar.caloriecompass.onBoarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.data.UserRepository
import com.prafullkumar.caloriecompass.data.local.UserDataEntity
import kotlinx.coroutines.launch


enum class Gender(val value: String) {
    MALE("Male"), FEMALE("Female")
}

enum class WeighingUnit(val value: String) {
    KG("Kg"), POUND("Pound")
}

enum class Goal(val value: String) {
    LOSE_WEIGHT("🚵‍♀️ Lose Weight"), GAIN_WEIGHT("💪 Gain Weight"), MAINTAIN_WEIGHT("🍎 Maintain Weight")
}

enum class ActivityLevel(val value: String, val description: String, val emoji: String) {
    SEDENTARY(
        "Sedentary",
        "Little to no exercise, mostly sitting throughout the day (e.g., desk job, minimal movement).",
        "🛋️"
    ),
    LIGHTLY_ACTIVE(
        "Lightly Active",
        "Light daily activity or low-intensity exercise 1-3 times a week (e.g., walking, household chores).",
        "🚶"
    ),
    MODERATELY_ACTIVE(
        "Moderately Active",
        "Moderate exercise or sports 3-5 days a week (e.g., jogging, cycling, gym workouts).",
        "🏃"
    ),
    VERY_ACTIVE(
        "Very Active",
        "Hard exercise or sports 6-7 days a week, physically demanding job (e.g., construction worker, athlete).",
        "🏋️"
    ),
    SUPER_ACTIVE(
        "Super Active",
        "Very intense exercise daily or a highly active job (e.g., professional athlete, military training).",
        "🔥"
    )
}

data class UiState(
    val gender: Gender = Gender.MALE,
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val weighingUnit: WeighingUnit = WeighingUnit.KG,
    val height: String = "",
    val goal: Goal = Goal.MAINTAIN_WEIGHT,
    val activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
)

class OnBoardingViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(UiState())

    fun onNextClicked() {
        viewModelScope.launch {
            userRepository.getAddUserData(
                userDataEntity = UserDataEntity(
                    userName = uiState.name,
                    userWeight = uiState.weight.toInt(),
                    userHeight = uiState.height.toInt(),
                    userWeightingUnit = uiState.weighingUnit.value,
                    userGoal = uiState.goal.value,
                    userGender = uiState.gender.value,
                    userAge = uiState.age.toInt(),
                    userActivityLevel = uiState.activityLevel.value
                )
            )
        }
    }

    fun addActivityLevel() {
        viewModelScope.launch {
            userRepository.addActivityLevel(
                activityLevel = uiState.activityLevel.value
            )
        }
    }

    fun canMoveToActivitySelectionScreen(): Boolean {
        return uiState.name.isNotEmpty() && uiState.age.isNotEmpty() && uiState.weight.isNotEmpty() && uiState.height.isNotEmpty()
    }
}