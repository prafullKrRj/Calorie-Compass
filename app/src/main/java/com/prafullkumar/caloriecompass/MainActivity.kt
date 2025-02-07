package com.prafullkumar.caloriecompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.caloriecompass.app.home.ui.calorieIntake.CalorieIntakeScreen
import com.prafullkumar.caloriecompass.app.home.ui.macroNutrient.MacroNutrientScreen
import com.prafullkumar.caloriecompass.app.home.ui.main.HomeScreen
import com.prafullkumar.caloriecompass.app.onBoarding.ui.ActivityLevelScreen
import com.prafullkumar.caloriecompass.app.onBoarding.ui.OnBoardingFormScreen
import com.prafullkumar.caloriecompass.app.onBoarding.ui.OnBoardingScreen
import com.prafullkumar.caloriecompass.app.onBoarding.ui.OnBoardingViewModel
import com.prafullkumar.caloriecompass.app.settings.ui.SettingsScreen
import com.prafullkumar.caloriecompass.common.data.SharedPrefManager
import com.prafullkumar.caloriecompass.ui.theme.CalorieCompassTheme
import org.koin.android.ext.android.getKoin
import org.koin.androidx.compose.getViewModel

/**
 * Main activity of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val onBoarded = getKoin().get<SharedPrefManager>().getOnBoarded()
            CalorieCompassTheme {
                NavGraph(onBoarded)
            }
        }
    }
}

/**
 * Navigation graph for the application.
 *
 * @param onBoarded Boolean indicating if the user has completed onboarding.
 */
@Composable
fun NavGraph(onBoarded: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = if (onBoarded) MainRoutes.Home else MainRoutes.OnBoarding
    ) {
        onBoardingScreens(navController)
        homeScreens(navController)
    }
}

/**
 * Main screen with a bottom navigation bar.
 *
 * @param navController NavController for navigation.
 * @param destination Current destination.
 */
@Composable
fun MainScreen(
    navController: NavController,
    destination: Any
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = HomeRoutes.HomeScreen == destination,
                    onClick = { navController.navigate(HomeRoutes.HomeScreen) },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    },
                )
                NavigationBarItem(
                    selected = HomeRoutes.SettingsScreen == destination,
                    onClick = { navController.navigate(HomeRoutes.SettingsScreen) },
                    icon = {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    },
                )
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (destination) {
                is HomeRoutes.HomeScreen -> HomeScreen(getViewModel(), navController)
                is HomeRoutes.SettingsScreen -> SettingsScreen(getViewModel(), navController)
            }
        }
    }
}

/**
 * Adds onboarding screens to the navigation graph.
 *
 * @param navController NavController for navigation.
 */
fun NavGraphBuilder.onBoardingScreens(navController: NavController) {
    navigation<MainRoutes.OnBoarding>(startDestination = OnBoardingRoutes.OnBoardingScreen) {
        composable<OnBoardingRoutes.OnBoardingScreen> {
            OnBoardingScreen(navController)
        }
        composable<OnBoardingRoutes.OnBoardingForm> {
            OnBoardingFormScreen(getViewModel<OnBoardingViewModel>(), navController)
        }
        composable<OnBoardingRoutes.OnBoardingActivityLevel> {
            ActivityLevelScreen(getViewModel<OnBoardingViewModel>(), navController)
        }
    }
}

/**
 * Adds home screens to the navigation graph.
 *
 * @param navController NavController for navigation.
 */
fun NavGraphBuilder.homeScreens(navController: NavController) {
    navigation<MainRoutes.Home>(startDestination = HomeRoutes.HomeScreen) {
        composable<HomeRoutes.HomeScreen> {
            MainScreen(navController, HomeRoutes.HomeScreen)
        }
        composable<HomeRoutes.MacroNutrientScreen> {
            MacroNutrientScreen(getViewModel(), navController)
        }
        composable<HomeRoutes.CalorieIntakeScreen> {
            CalorieIntakeScreen(getViewModel(), navController)
        }
        composable<HomeRoutes.SettingsScreen> {
            MainScreen(navController, HomeRoutes.SettingsScreen)
        }
    }
}