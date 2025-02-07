package com.prafullkumar.caloriecompass.app.settings.domain

import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity

/**
 * Interface for managing user settings.
 */
interface SettingsRepository {

    /**
     * Updates the user details in the repository.
     *
     * @param userDataEntity The user data entity containing updated user details.
     */
    suspend fun updateUserDetails(userDataEntity: UserDataEntity)

    /**
     * Retrieves the user details from the repository.
     *
     * @return A list of user data entities.
     */
    suspend fun getUserDetails(): List<UserDataEntity>
}