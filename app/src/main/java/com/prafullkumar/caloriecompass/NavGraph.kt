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
}

sealed interface OnBoardingRoutes {
    @Serializable
    data object OnBoardingScreen : OnBoardingRoutes

    @Serializable
    data object OnBoardingForm : OnBoardingRoutes
}