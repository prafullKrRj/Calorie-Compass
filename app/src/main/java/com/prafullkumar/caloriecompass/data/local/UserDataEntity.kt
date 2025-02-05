package com.prafullkumar.caloriecompass.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity(
    @PrimaryKey
    val id: Int = 0,
    val userName: String,
    val userWeight: Int,
    val userHeight: Int,
    val userWeightingUnit: String,
    val userGoal: String,
    val userGender: String,
    val userAge: Int,
    val userActivityLevel: String
)