package com.prafullkumar.caloriecompass.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user data entity in the local database.
 *
 * @property id The unique identifier for the user.
 * @property userName The name of the user.
 * @property userWeight The weight of the user.
 * @property userHeight The height of the user.
 * @property userWeightingUnit The unit of the user's weight (e.g., kg, lbs).
 * @property userGoal The goal of the user (e.g., weight loss, maintenance).
 * @property userGender The gender of the user.
 * @property userAge The age of the user.
 * @property userActivityLevel The activity level of the user (e.g., sedentary, active).
 */
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