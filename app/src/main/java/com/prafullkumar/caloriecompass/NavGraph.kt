package com.prafullkumar.caloriecompass

import kotlinx.serialization.Serializable

sealed interface MainRoutes {
    @Serializable
    data object Home : MainRoutes

    @Serializable
    data object OnBoarding : MainRoutes
}

sealed interface HomeRoutes {
    @Serializable
    data object HomeScreen : HomeRoutes

    @Serializable
    data object LoggingScreen : HomeRoutes

    @Serializable
    data object SettingsScreen : HomeRoutes
}

sealed interface OnBoardingRoutes {
    @Serializable
    data object OnBoardingScreen : OnBoardingRoutes

    @Serializable
    data object OnBoardingForm : OnBoardingRoutes

    @Serializable
    data object OnBoardingActivityLevel : OnBoardingRoutes
}