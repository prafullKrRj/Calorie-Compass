package com.prafullkumar.caloriecompass.app.settings.di

import com.prafullkumar.caloriecompass.app.settings.data.SettingsRepositoryImpl
import com.prafullkumar.caloriecompass.app.settings.domain.SettingsRepository
import com.prafullkumar.caloriecompass.app.settings.ui.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    viewModel { SettingsViewModel(get(), androidContext()) }
}