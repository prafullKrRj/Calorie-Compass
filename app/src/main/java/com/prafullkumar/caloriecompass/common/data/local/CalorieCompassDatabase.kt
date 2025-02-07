package com.prafullkumar.caloriecompass.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for the Calorie Compass application.
 * This database contains the UserDataEntity table.
 *
 * @Database annotation specifies the entities and version of the database.
 * @property userDataEntityDao Provides access to UserDataEntity data operations.
 */
@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)
abstract class CalorieCompassDatabase : RoomDatabase() {
    /**
     * Abstract method to get the DAO for UserDataEntity.
     *
     * @return UserDataEntityDao The DAO for UserDataEntity.
     */
    abstract fun userDataEntityDao(): UserDataEntityDao
}