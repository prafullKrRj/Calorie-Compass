package com.prafullkumar.caloriecompass.app.log

import androidx.room.Room
import com.prafullkumar.caloriecompass.app.log.data.FitnessDatabase
import com.prafullkumar.caloriecompass.app.log.data.FitnessRepository
import com.prafullkumar.caloriecompass.app.log.data.LoggingDao
import com.prafullkumar.caloriecompass.app.log.ui.FitnessLoggingViewModel
import com.prafullkumar.caloriecompass.app.log.ui.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val logModule = module {
    single<FitnessDatabase> {
        Room.databaseBuilder(androidContext(), FitnessDatabase::class.java, "fitness.db")
            .build()
    }
    single<LoggingDao> { get<FitnessDatabase>().loggingDao() }
    single<FitnessRepository> { FitnessRepository(get()) }
    viewModel { FitnessLoggingViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}