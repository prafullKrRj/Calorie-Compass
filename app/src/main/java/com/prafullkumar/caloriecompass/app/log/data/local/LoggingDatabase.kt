package com.prafullkumar.caloriecompass.app.log.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FoodLogEntity::class, ExerciseLogEntity::class], version = 1)
abstract class LoggingDatabase : RoomDatabase() {
    abstract fun loggingDao(): LoggingDao
}