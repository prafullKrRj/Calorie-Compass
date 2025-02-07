package com.prafullkumar.caloriecompass.common.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing user data in the database.
 */
@Dao
interface UserDataEntityDao {

    /**
     * Inserts or updates a user data entity in the database.
     *
     * @param userDataEntity The user data entity to be inserted or updated.
     * @return The row ID of the newly inserted or updated user data entity.
     */
    @Upsert
    suspend fun insertUserData(userDataEntity: UserDataEntity): Long

    /**
     * Retrieves a flow of all user data entities from the database.
     *
     * @return A flow of a list of user data entities.
     */
    @Query("SELECT * FROM user_data")
    fun getUserDataFlow(): Flow<List<UserDataEntity>>

    /**
     * Retrieves all user data entities from the database.
     *
     * @return A list of user data entities.
     */
    @Query("SELECT * FROM user_data")
    suspend fun getUserData(): List<UserDataEntity>

    /**
     * Updates the activity level of the user with ID 0.
     *
     * @param activityLevel The new activity level to be set.
     */
    @Query("UPDATE user_data SET userActivityLevel = :activityLevel where id = 0")
    suspend fun updateActivityLevel(activityLevel: String)
}