package com.prafullkumar.caloriecompass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)
abstract class CalorieCompassDatabase : RoomDatabase() {
    abstract fun userDataEntityDao(): UserDataEntityDao
}