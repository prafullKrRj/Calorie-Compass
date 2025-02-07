package com.prafullkumar.caloriecompass

import android.app.Application
import androidx.room.Room
import com.prafullkumar.caloriecompass.app.home.di.homeModule
import com.prafullkumar.caloriecompass.app.log.logModule
import com.prafullkumar.caloriecompass.app.onBoarding.di.onBoardingModule
import com.prafullkumar.caloriecompass.app.settings.di.settingsModule
import com.prafullkumar.caloriecompass.common.data.SharedPrefManager
import com.prafullkumar.caloriecompass.common.data.local.CalorieCompassDatabase
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntityDao
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
}


class CalorieCompassApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalorieCompassApplication)
            androidLogger()
            modules(appModule, onBoardingModule, homeModule, logModule, settingsModule)
        }
    }
}