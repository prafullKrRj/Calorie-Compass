package com.prafullkumar.caloriecompass.app.log.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.scale
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
import com.prafullkumar.caloriecompass.app.log.domain.model.ExerciseLog
import com.prafullkumar.caloriecompass.app.log.domain.model.FoodLog
import com.prafullkumar.caloriecompass.app.log.ui.components.ExerciseLogCard
import com.prafullkumar.caloriecompass.app.log.ui.components.FoodLogCard
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Composable function for the Fitness Logging Screen.
 * Displays the UI for logging food and exercise, and shows the daily summary.
 *
 * @param viewModel The ViewModel for managing fitness logging data.
 * @param innerPadding Padding values for the inner content.
 * @param navController Navigation controller for navigating between screens.
 */
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
                .padding(top = padding.calculateTopPadding())
                .padding(bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
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


            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    DailySummaryCard(
                        dailyCaloriesConsumed = dailyCaloriesConsumed,
                        dailyCaloriesBurned = dailyCaloriesBurned
                    )
                }
                if (foodLogs.isNotEmpty()) {
                    item {
                        Text(
                            "Recent Food Logs",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(foodLogs) { foodLog ->
                        FoodLogCard(
                            foodLog = foodLog
                        )
                    }
                }
                if (exerciseLogs.isNotEmpty()) {
                    item {
                        Text(
                            "Recent Exercise Logs",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(exerciseLogs) { exerciseLog ->
                        ExerciseLogCard(
                            exerciseLog = exerciseLog
                        )
                    }
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
fun DailySummaryCard(
    dailyCaloriesConsumed: Double,
    dailyCaloriesBurned: Double,
    modifier: Modifier = Modifier
) {
    // Animation for the numbers
    val animatedCaloriesConsumed by animateFloatAsState(
        targetValue = dailyCaloriesConsumed.toFloat(),
        animationSpec = tween(1000)
    )
    val animatedCaloriesBurned by animateFloatAsState(
        targetValue = dailyCaloriesBurned.toFloat(),
        animationSpec = tween(1000)
    )

    // Subtle pulse animation for icons
    val infiniteTransition = rememberInfiniteTransition(label = "iconPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconScale"
    )

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Today's Summary",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Calories Consumed Column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_restaurant_24),
                        contentDescription = "Calories Consumed",
                        modifier = Modifier
                            .size(32.dp)
                            .scale(scale),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = animatedCaloriesConsumed.roundToInt().toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Consumed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }

                // Vertical Divider
                Divider(
                    modifier = Modifier
                        .height(80.dp)
                        .width(1.dp)
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
                )

                // Calories Burned Column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_local_fire_department_24),
                        contentDescription = "Calories Burned",
                        modifier = Modifier
                            .size(32.dp)
                            .scale(scale),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = animatedCaloriesBurned.roundToInt().toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Burned",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Net Calories Section
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Net Calories",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = (dailyCaloriesConsumed - dailyCaloriesBurned).roundToInt().toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Composable function for the Food Logging Dialog.
 * Displays a dialog for logging food details.
 *
 * @param onDismiss Callback when the dialog is dismissed.
 * @param onSave Callback when the food log is saved.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodLoggingDialog(
    onDismiss: () -> Unit,
    onSave: (FoodLog) -> Unit
) {
    var mealSelectionExpanded by remember { mutableStateOf(false) }
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf(MealType.BREAKFAST) }
    var error by remember { mutableStateOf(false) }
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
                    expanded = mealSelectionExpanded,
                    onExpandedChange = {
                        mealSelectionExpanded = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    OutlinedTextField(
                        value = mealType.name.capitalize(Locale.ROOT),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Meal Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = mealSelectionExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = mealSelectionExpanded,
                        onDismissRequest = { mealSelectionExpanded = false }
                    ) {
                        MealType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name.capitalize(Locale.ROOT)) },
                                onClick = {
                                    mealType = type
                                    mealSelectionExpanded = false
                                }
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
                if (error) {
                    Text(
                        "Please fill all necessary fields!!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
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
                            if (foodName.isEmpty() || calories.isEmpty()) {
                                error = true
                                return@Button
                            }
                            val foodLog = FoodLog(
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

/**
 * Composable function for the Exercise Logging Dialog.
 * Displays a dialog for logging exercise details.
 *
 * @param onDismiss Callback when the dialog is dismissed.
 * @param onSave Callback when the exercise log is saved.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseLoggingDialog(
    onDismiss: () -> Unit,
    onSave: (ExerciseLog) -> Unit
) {
    var exerciseType by remember { mutableStateOf(ExerciseType.GYM_WEIGHT_TRAINING) }
    var duration by remember { mutableStateOf("") }
    var caloriesBurned by remember { mutableStateOf("") }
    var exerciseSelectionExpanded by remember { mutableStateOf(false) }
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
                    expanded = exerciseSelectionExpanded,
                    onExpandedChange = {
                        exerciseSelectionExpanded = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    OutlinedTextField(
                        value = exerciseType.name.replace("_", " ").capitalize(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Exercise Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = exerciseSelectionExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = exerciseSelectionExpanded,
                        onDismissRequest = { exerciseSelectionExpanded = false }
                    ) {
                        ExerciseType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name.replace("_", " ").capitalize()) },
                                onClick = {
                                    exerciseType = type
                                    exerciseSelectionExpanded = false
                                }
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
                            val exerciseLog = ExerciseLog(
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