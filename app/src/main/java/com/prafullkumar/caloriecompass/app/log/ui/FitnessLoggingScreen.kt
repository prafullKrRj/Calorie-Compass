package com.prafullkumar.caloriecompass.app.log.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.HomeRoutes
import com.prafullkumar.caloriecompass.R
import com.prafullkumar.caloriecompass.app.log.ExerciseType
import com.prafullkumar.caloriecompass.app.log.MealType
import com.prafullkumar.caloriecompass.app.log.data.ExerciseLogEntity
import com.prafullkumar.caloriecompass.app.log.data.FoodLogEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


// Composable UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessLoggingScreen(
    viewModel: FitnessLoggingViewModel,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val foodLogs by viewModel.foodLogs.collectAsState()
    val exerciseLogs by viewModel.exerciseLogs.collectAsState()
    val dailyCaloriesConsumed by viewModel.dailyCaloriesConsumed.collectAsState()
    val dailyCaloriesBurned by viewModel.dailyCaloriesBurned.collectAsState()

    var showFoodDialog by remember { mutableStateOf(false) }
    var showExerciseDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Fitness Tracker") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(HomeRoutes.LoggingHistoryScreen)
                    }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.baseline_history_24),
                            contentDescription = "History"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = {
                    showFoodDialog = true
                }, modifier = Modifier.weight(.5f)) {
                    Text("Log Food")
                }
                OutlinedButton(onClick = {
                    showExerciseDialog = true
                }, modifier = Modifier.weight(.5f)) {
                    Text("Log Exercise")
                }
            }

            // Daily Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Today's Summary",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Calories Consumed: ${dailyCaloriesConsumed.roundToInt()}")
                        Text("Calories Burned: ${dailyCaloriesBurned.roundToInt()}")
                    }
                }
            }

            // Food Logs Section
            Text(
                "Recent Food Logs",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn {
                items(foodLogs) { foodLog ->
                    FoodLogCard(
                        food = foodLog
                    )
                }
            }

            // Exercise Logs Section
            Text(
                "Recent Exercise Logs",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn {
                items(exerciseLogs) { exerciseLog ->
                    ExerciseLogCard(
                        exercise = exerciseLog
                    )
                }
            }
        }
    }

    // Food Logging Dialog
    if (showFoodDialog) {
        FoodLoggingDialog(
            onDismiss = { showFoodDialog = false },
            onSave = { foodLog ->
                viewModel.addFoodLog(foodLog)
                showFoodDialog = false
            }
        )
    }

    // Exercise Logging Dialog
    if (showExerciseDialog) {
        ExerciseLoggingDialog(
            onDismiss = { showExerciseDialog = false },
            onSave = { exerciseLog ->
                viewModel.addExerciseLog(exerciseLog)
                showExerciseDialog = false
            }
        )
    }
}

@Composable
fun ExerciseLogCard(
    exercise: ExerciseLogEntity
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6F2FF)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exercise Icon
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_fitness_center_24),
                contentDescription = "Exercise",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp),
                tint = Color(0xFF1A73E8)
            )

            Column {
                // Exercise Type and Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = exercise.exerciseType.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(
                            Date(
                                exercise.date
                            )
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                // Duration and Calories
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Duration: ${exercise.duration} mins",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Calories Burned: ${exercise.caloriesBurned.toInt()} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Food Logging Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodLoggingDialog(
    onDismiss: () -> Unit,
    onSave: (FoodLogEntity) -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf(MealType.BREAKFAST) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Log Food",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Meal Type Selector
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = {}
                    ) {
                        MealType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name.capitalize()) },
                                onClick = { mealType = type }
                            )
                        }
                    }
                }

                // Input Fields
                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = calories,
                        onValueChange = { calories = it },
                        label = { Text("Calories") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text("Protein (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = carbs,
                        onValueChange = { carbs = it },
                        label = { Text("Carbs (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = fats,
                        onValueChange = { fats = it },
                        label = { Text("Fats (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val foodLog = FoodLogEntity(
                                mealType = mealType,
                                foodName = foodName,
                                calories = calories.toDoubleOrNull() ?: 0.0,
                                protein = protein.toDoubleOrNull() ?: 0.0,
                                carbs = carbs.toDoubleOrNull() ?: 0.0,
                                fats = fats.toDoubleOrNull() ?: 0.0
                            )
                            onSave(foodLog)
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

// Exercise Logging Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseLoggingDialog(
    onDismiss: () -> Unit,
    onSave: (ExerciseLogEntity) -> Unit
) {
    var exerciseType by remember { mutableStateOf(ExerciseType.GYM_WEIGHT_TRAINING) }
    var duration by remember { mutableStateOf("") }
    var caloriesBurned by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Log Exercise",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Exercise Type Selector
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = {}
                    ) {
                        ExerciseType.values().forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name.replace("_", " ").capitalize()) },
                                onClick = { exerciseType = type }
                            )
                        }
                    }
                }

                // Input Fields
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (minutes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = caloriesBurned,
                    onValueChange = { caloriesBurned = it },
                    label = { Text("Calories Burned") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val exerciseLog = ExerciseLogEntity(
                                exerciseType = exerciseType,
                                duration = duration.toIntOrNull() ?: 0,
                                caloriesBurned = caloriesBurned.toDoubleOrNull() ?: 0.0
                            )
                            onSave(exerciseLog)
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun FoodLogCard(
    food: FoodLogEntity
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F4F8)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Food Name and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = food.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = SimpleDateFormat(
                        "MMM dd, yyyy",
                        Locale.getDefault()
                    ).format(Date(food.date)),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Meal Type
            Text(
                text = food.mealType.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Calories
            Text(
                text = "Calories: ${food.calories.toInt()} kcal",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 12.dp)
            )

            // Nutritional Information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NutrientText(label = "Protein", value = food.protein)
                NutrientText(label = "Carbs", value = food.carbs)
                NutrientText(label = "Fats", value = food.fats)
            }
        }
    }
}

@Composable
private fun NutrientText(label: String, value: Double?) {
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = value?.let { "${it.toInt()} g" } ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}