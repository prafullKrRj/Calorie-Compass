package com.prafullkumar.caloriecompass.app.onBoarding.data

import com.prafullkumar.caloriecompass.app.onBoarding.domain.UserRepository
import com.prafullkumar.caloriecompass.common.data.SharedPrefManager
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntityDao
import kotlinx.coroutines.coroutineScope

/**
 * Implementation of the UserRepository interface.
 *
 * @property userDataEntityDao Data Access Object for user data.
 * @property sharedPrefManager Manager for shared preferences.
 */
class UserRepositoryImpl(
    private val userDataEntityDao: UserDataEntityDao,
    private val sharedPrefManager: SharedPrefManager
) : UserRepository {

    /**
     * Inserts user data into the database.
     *
     * @param userDataEntity The user data entity to be inserted.
     * @return The row ID of the newly inserted user data.
     */
    override suspend fun getAddUserData(userDataEntity: UserDataEntity): Long {
        return userDataEntityDao.insertUserData(userDataEntity)
    }

    /**
     * Updates the activity level of the user.
     *
     * @param activityLevel The new activity level to be set.
     */
    override suspend fun addActivityLevel(activityLevel: String) {
        coroutineScope {
            sharedPrefManager.setOnBoarded()
        }
        coroutineScope {
            userDataEntityDao.updateActivityLevel(activityLevel)
        }
    }
}