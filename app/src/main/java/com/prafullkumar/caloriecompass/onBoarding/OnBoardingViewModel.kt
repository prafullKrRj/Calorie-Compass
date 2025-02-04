package com.prafullkumar.caloriecompass.onBoarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


enum class Gender(val value: String) {
    MALE("Male"), FEMALE("Female")
}

enum class WeighingUnit(val value: String) {
    KG("Kg"), POUND("Pound")
}

enum class HeightUnit(val value: String) {
    CM("cm"), FOOT("Foot")
}

enum class Goal(val value: String) {
    LOSE_WEIGHT("🚵‍♀️ Lose Weight"), GAIN_WEIGHT("💪 Gain Weight"), MAINTAIN_WEIGHT("🍎 Maintain Weight")
}

data class UiState(
    val gender: Gender = Gender.MALE,
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val weighingUnit: WeighingUnit = WeighingUnit.KG,
    val height: String = "",
    val heightUnit: HeightUnit = HeightUnit.CM,
    val goal: Goal = Goal.MAINTAIN_WEIGHT
)

class OnBoardingViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState())
}