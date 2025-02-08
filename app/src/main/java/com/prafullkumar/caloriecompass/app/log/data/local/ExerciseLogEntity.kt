package com.prafullkumar.caloriecompass.app.log.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prafullkumar.caloriecompass.app.log.ExerciseType

@Entity(tableName = "exercise_log")
data class ExerciseLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val exerciseType: ExerciseType,
    val duration: Int, // minutes
    val caloriesBurned: Double
)
