package com.prafullkumar.caloriecompass.app.home.di

import com.prafullkumar.caloriecompass.app.home.data.HomeRepositoryImpl
import com.prafullkumar.caloriecompass.app.home.domain.repository.HomeRepository
import com.prafullkumar.caloriecompass.app.home.ui.calorieIntake.CalorieIntakeViewModel
import com.prafullkumar.caloriecompass.app.home.ui.macroNutrient.MacroNutrientViewModel
import com.prafullkumar.caloriecompass.app.home.ui.main.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Define a Koin module for dependency injection in the Home feature
val homeModule = module {
    // Provide a singleton instance of HomeRepository using HomeRepositoryImpl
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    // Provide a ViewModel instance for HomeViewModel
    viewModel<HomeViewModel> { HomeViewModel(get()) }

    // Provide a ViewModel instance for MacroNutrientViewModel
    viewModel<MacroNutrientViewModel> { MacroNutrientViewModel(get()) }

    // Provide a ViewModel instance for CalorieIntakeViewModel
    viewModel<CalorieIntakeViewModel> { CalorieIntakeViewModel(get()) }
}