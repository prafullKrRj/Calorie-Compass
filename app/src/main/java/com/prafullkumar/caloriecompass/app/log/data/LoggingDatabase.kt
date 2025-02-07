package com.prafullkumar.caloriecompass.app.log.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FoodLogEntity::class, ExerciseLogEntity::class], version = 1)
abstract class FitnessDatabase : RoomDatabase() {
    abstract fun loggingDao(): LoggingDao
}