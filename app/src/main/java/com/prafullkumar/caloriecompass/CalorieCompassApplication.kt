package com.prafullkumar.caloriecompass

import android.app.Application
import com.prafullkumar.caloriecompass.data.SharedPrefManager
import com.prafullkumar.caloriecompass.onBoarding.OnBoardingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single<SharedPrefManager> {
        SharedPrefManager(androidContext())
    }
    viewModel { OnBoardingViewModel() }
}


class CalorieCompassApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalorieCompassApplication)
            androidLogger()
            modules(appModule)
        }
    }
}