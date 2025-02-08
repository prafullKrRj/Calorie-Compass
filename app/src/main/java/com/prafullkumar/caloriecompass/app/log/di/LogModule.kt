package com.prafullkumar.caloriecompass.app.log.di

import androidx.room.Room
import com.prafullkumar.caloriecompass.app.log.data.local.LoggingDao
import com.prafullkumar.caloriecompass.app.log.data.local.LoggingDatabase
import com.prafullkumar.caloriecompass.app.log.data.repository.FitnessRepositoryImpl
import com.prafullkumar.caloriecompass.app.log.domain.repository.FitnessRepository
import com.prafullkumar.caloriecompass.app.log.ui.FitnessLoggingViewModel
import com.prafullkumar.caloriecompass.app.log.ui.history.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val logModule = module {
    single<LoggingDatabase> {
        Room.databaseBuilder(androidContext(), LoggingDatabase::class.java, "fitness.db")
            .build()
    }
    single<LoggingDao> { get<LoggingDatabase>().loggingDao() }
    single<FitnessRepository> { FitnessRepositoryImpl(get()) }
    viewModel { FitnessLoggingViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}