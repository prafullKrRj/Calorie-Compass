package com.prafullkumar.caloriecompass.app.onBoarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.HomeRoutes
import com.prafullkumar.caloriecompass.MainRoutes
import com.prafullkumar.caloriecompass.R

@Composable
fun ActivityLevelScreen(
    onBoardingViewModel: OnBoardingViewModel, navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.activity_level),
            contentDescription = "Activity Level",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = "Tell us about your activity level",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
        )
        ActivityLevel.entries.forEach { activityLevel ->
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), onClick = {
                    onBoardingViewModel.uiState = onBoardingViewModel.uiState.copy(
                        activityLevel = activityLevel
                    )
                }, colors = CardDefaults.outlinedCardColors(
                    containerColor = if (onBoardingViewModel.uiState.activityLevel == activityLevel) {
                        MaterialTheme.colorScheme.surfaceBright
                    } else {
                        MaterialTheme.colorScheme.surfaceDim
                    }
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(activityLevel.emoji, fontSize = 20.sp)
                        Text(activityLevel.value, fontSize = 20.sp, modifier = Modifier.weight(1f))
                    }
                    Text(
                        activityLevel.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        Button(
            modifier = Modifier.padding(vertical = 16.dp), onClick = {
                onBoardingViewModel.addActivityLevel()
                navController.navigate(HomeRoutes.HomeScreen) {
                    popUpTo(MainRoutes.OnBoarding) { inclusive = true }
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surfaceBright
            )
        ) {
            Text(
                "Calculate TDEE", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 22.dp)
            )
        }
    }
}