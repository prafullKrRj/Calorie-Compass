package com.prafullkumar.caloriecompass.app.onBoarding.domain

import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity

interface UserRepository {
    suspend fun getAddUserData(userDataEntity: UserDataEntity): Long

    suspend fun addActivityLevel(activityLevel: String)
}