package com.prafullkumar.caloriecompass.data

import com.prafullkumar.caloriecompass.data.local.UserDataEntity
import com.prafullkumar.caloriecompass.data.local.UserDataEntityDao
import kotlinx.coroutines.coroutineScope

class UserRepository(
    private val userDataEntityDao: UserDataEntityDao,
    private val sharedPrefManager: SharedPrefManager
) {
    suspend fun getAddUserData(userDataEntity: UserDataEntity): Long {

        return userDataEntityDao.insertUserData(userDataEntity)
    }

    suspend fun addActivityLevel(activityLevel: String) {
        coroutineScope {
            sharedPrefManager.setOnBoarded()
        }
        coroutineScope {
            userDataEntityDao.updateActivityLevel(activityLevel)
        }
    }
}