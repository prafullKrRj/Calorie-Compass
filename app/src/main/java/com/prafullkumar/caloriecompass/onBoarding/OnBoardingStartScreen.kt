package com.prafullkumar.caloriecompass.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.OnBoardingRoutes
import com.prafullkumar.caloriecompass.R

@Composable
fun OnBoardingScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally
    ) {
        Column(
            Modifier
                .weight(.8f)
                .fillMaxWidth()
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearOutSlowInEasing
                    )
                ),
            ) {
                Image(
                    painter = painterResource(R.drawable.on_boarding_logo),
                    contentDescription = "OnBoarding Image",
                    contentScale = ContentScale.FillWidth,
                )
            }
            Text(
                "Start your fitness journey with \nour TDEE calculator!",
                modifier = Modifier.align(CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "Confused about calories? No sweat! \nWe'll help you figure it out.",
                modifier = Modifier.align(CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                navController.navigate(OnBoardingRoutes.OnBoardingForm)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surfaceBright
            )
        ) {
            Text("Start", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 22.dp))
        }
    }
}