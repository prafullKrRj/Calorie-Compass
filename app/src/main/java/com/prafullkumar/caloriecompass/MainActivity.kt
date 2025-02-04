package com.prafullkumar.caloriecompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.caloriecompass.data.SharedPrefManager
import com.prafullkumar.caloriecompass.onBoarding.OnBoardingFormScreen
import com.prafullkumar.caloriecompass.onBoarding.OnBoardingScreen
import com.prafullkumar.caloriecompass.onBoarding.OnBoardingViewModel
import com.prafullkumar.caloriecompass.ui.theme.CalorieCompassTheme
import org.koin.android.ext.android.getKoin
import org.koin.androidx.compose.getViewModel

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

@Composable
fun NavGraph(onBoarded: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (onBoarded) MainRoutes.Home else MainRoutes.OnBoarding
    ) {
        onBoardingScreens(navController)
        homeScreens(navController)
    }
}

fun NavGraphBuilder.onBoardingScreens(navController: NavController) {
    navigation<MainRoutes.OnBoarding>(startDestination = OnBoardingRoutes.OnBoardingForm) {
        composable<OnBoardingRoutes.OnBoardingScreen> {
            OnBoardingScreen(navController)
        }
        composable<OnBoardingRoutes.OnBoardingForm> {
            OnBoardingFormScreen(getViewModel<OnBoardingViewModel>(), navController)
        }
    }
}

fun NavGraphBuilder.homeScreens(navController: NavController) {
    navigation<MainRoutes.Home>(startDestination = HomeRoutes.HomeScreen) {
        composable<HomeRoutes.HomeScreen> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Home")
            }
        }
    }
}