package com.prafullkumar.caloriecompass.app.home.ui.calorieIntake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.R

/**
 * Composable function to display the Calorie Intake screen.
 *
 * @param viewModel The ViewModel for the Calorie Intake screen.
 * @param navController The NavController for navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieIntakeScreen(viewModel: CalorieIntakeViewModel, navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Calorie Intake")
        }, navigationIcon = {
            IconButton(onClick = navController::popBackStack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (viewModel.uiState.loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (viewModel.uiState.error.isNotEmpty()) {
                Text(text = viewModel.uiState.error)
            } else {
                CalorieIntakeDisplay(viewModel.uiState.data)
            }
        }
    }
}

/**
 * Composable function to display the calorie intake data.
 *
 * @param intakeData The data to display, organized by IntakeFor and IntakeLevel.
 */
@Composable
fun CalorieIntakeDisplay(
    intakeData: Map<IntakeFor, Map<IntakeLevel, IntakeCaloriesPerDay>>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(intakeData.entries.toList()) { (intakeFor, levelData) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Calorie Intake for ${intakeFor.value}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_fitness_center_24),
                            contentDescription = "Calorie Intake",
                            tint = Color(0xFF4CAF50)
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray.copy(alpha = 0.3f)
                    )

                    levelData.entries.forEachIndexed { index, (level, intake) ->
                        CalorieIntakeRow(
                            level = level,
                            calories = intake.calories,
                            percentage = intake.percentage.toDouble(),
                            lossPerWeek = intake.lossPerWeek.toDouble(),
                            isLast = index == levelData.size - 1
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display a row of calorie intake data.
 *
 * @param level The intake level.
 * @param calories The number of calories.
 * @param percentage The percentage of TDEE.
 * @param lossPerWeek The weekly weight loss or gain.
 * @param isLast Whether this is the last row in the list.
 */
@Composable
fun CalorieIntakeRow(
    level: IntakeLevel,
    calories: Int,
    percentage: Double,
    lossPerWeek: Double,
    isLast: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level: $level",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.7f)
            )
            Text(
                text = "$calories Calories",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Percentage",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.5f)
            )
            Text(
                text = "${percentage.format(1)}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFFFC107)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Weekly Loss",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.5f)
            )
            Text(
                text = "${lossPerWeek.format(1)} kg",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFF44336)
            )
        }

        if (!isLast) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Gray.copy(alpha = 0.2f)
            )
        }
    }
}

/**
 * Extension function for clean decimal formatting.
 *
 * @param digits The number of digits to format to.
 * @return The formatted string.
 */
fun Double.format(digits: Int) = "%.${digits}f".format(this)