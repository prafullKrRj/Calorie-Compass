package com.prafullkumar.caloriecompass.app.log.domain.model

import com.prafullkumar.caloriecompass.app.log.ExerciseType

data class ExerciseLog(
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val exerciseType: ExerciseType,
    val duration: Int, // minutes
    val caloriesBurned: Double
)