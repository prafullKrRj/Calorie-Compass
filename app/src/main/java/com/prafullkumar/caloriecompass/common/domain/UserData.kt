package com.prafullkumar.caloriecompass.common.domain

data class UserData(
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
