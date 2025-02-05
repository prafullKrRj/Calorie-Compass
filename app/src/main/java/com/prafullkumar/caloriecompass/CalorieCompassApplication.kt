package com.prafullkumar.caloriecompass

import android.app.Application
import androidx.room.Room
import com.prafullkumar.caloriecompass.app.home.HomeViewModel
import com.prafullkumar.caloriecompass.data.SharedPrefManager
import com.prafullkumar.caloriecompass.data.UserRepository
import com.prafullkumar.caloriecompass.data.local.CalorieCompassDatabase
import com.prafullkumar.caloriecompass.data.local.UserDataEntityDao
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
    single<CalorieCompassDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CalorieCompassDatabase::class.java,
            "calorie_compass_database"
        )
            .build()
    }
    single<UserDataEntityDao> {
        get<CalorieCompassDatabase>().userDataEntityDao()
    }
    single { UserRepository(userDataEntityDao = get(), sharedPrefManager = get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel<HomeViewModel> { HomeViewModel() }
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