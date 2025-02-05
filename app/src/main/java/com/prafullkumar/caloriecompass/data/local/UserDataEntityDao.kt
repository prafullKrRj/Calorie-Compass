package com.prafullkumar.caloriecompass.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataEntityDao {


    @Upsert
    suspend fun insertUserData(userDataEntity: UserDataEntity): Long

    @Query("SELECT * FROM user_data")
    fun getUserData(): Flow<List<UserDataEntity>>


    @Query("UPDATE user_data SET userActivityLevel = :activityLevel where id = 0")
    suspend fun updateActivityLevel(activityLevel: String)

}
