package com.prafullkumar.caloriecompass.app.onBoarding.di

import com.prafullkumar.caloriecompass.app.onBoarding.data.UserRepositoryImpl
import com.prafullkumar.caloriecompass.app.onBoarding.domain.UserRepository
import com.prafullkumar.caloriecompass.app.onBoarding.ui.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            userDataEntityDao = get(),
            sharedPrefManager = get()
        )
    }
    viewModel { OnBoardingViewModel(get()) }
}