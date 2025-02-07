package com.prafullkumar.caloriecompass.app.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.app.onBoarding.ui.ActivityLevel
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Gender
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Goal
import com.prafullkumar.caloriecompass.app.onBoarding.ui.WeighingUnit
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity

/**
 * Composable function to display the settings screen.
 *
 * @param viewModel The ViewModel that provides the data and handles the logic.
 * @param navController The NavController to handle navigation.
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    // State variables to hold user input
    var userName by remember { mutableStateOf(viewModel.data?.userName ?: "") }
    var weight by remember { mutableStateOf(viewModel.data?.userWeight?.toString() ?: "") }
    var height by remember { mutableStateOf(viewModel.data?.userHeight?.toString() ?: "") }
    var weightUnit by remember {
        mutableStateOf(
            viewModel.data?.userWeightingUnit ?: WeighingUnit.KG.name
        )
    }
    var goal by remember { mutableStateOf(viewModel.data?.userGoal ?: Goal.MAINTAIN_WEIGHT.name) }
    var gender by remember { mutableStateOf(viewModel.data?.userGender ?: Gender.MALE.name) }
    var age by remember { mutableStateOf(viewModel.data?.userAge?.toString() ?: "") }
    var activityLevel by remember {
        mutableStateOf(
            viewModel.data?.userActivityLevel ?: ActivityLevel.MODERATELY_ACTIVE.name
        )
    }
    var showWeighingUnitDropdownMenu by remember { mutableStateOf(false) }
    var selectedActivityLevel by remember { mutableStateOf(ActivityLevel.MODERATELY_ACTIVE) }

    // Effect to update state variables when viewModel data changes
    LaunchedEffect(viewModel.data) {
        viewModel.data?.let { data ->
            userName = data.userName
            weight = data.userWeight.toString()
            height = data.userHeight.toString()
            weightUnit = data.userWeightingUnit
            goal = data.userGoal
            gender = data.userGender
            age = data.userAge.toString()
            activityLevel = data.userActivityLevel
            selectedActivityLevel =
                ActivityLevel.entries.find { it.value == data.userActivityLevel }
                    ?: ActivityLevel.MODERATELY_ACTIVE
        }
    }

    // Main column layout for the settings screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title for the settings screen
        Text(
            text = "Profile Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Personal Information Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Input field for user name
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                // Row for weight input and unit selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull() != null) weight = it
                        },
                        label = { Text("Weight") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        trailingIcon = {
                            TextButton(onClick = {
                                showWeighingUnitDropdownMenu = !showWeighingUnitDropdownMenu
                            }) {
                                Text(weightUnit)
                            }
                            DropdownMenu(
                                expanded = showWeighingUnitDropdownMenu,
                                onDismissRequest = {
                                    showWeighingUnitDropdownMenu = false
                                }) {
                                WeighingUnit.entries.forEach {
                                    DropdownMenuItem(text = {
                                        Text(it.value)
                                    }, onClick = {
                                        showWeighingUnitDropdownMenu = false
                                        weightUnit = it.value
                                    })
                                }
                            }
                        }
                    )
                }

                // Input field for height
                OutlinedTextField(
                    value = height,
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) height = it },
                    label = { Text("Height (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                // Input field for age
                OutlinedTextField(
                    value = age,
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) age = it },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }

        // Gender Selection Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Gender.entries.forEach { genderOption ->
                        FilterChip(
                            selected = gender == genderOption.name,
                            onClick = { gender = genderOption.name },
                            label = { Text(genderOption.value) }
                        )
                    }
                }
            }
        }

        // Fitness Goals Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Fitness Goal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(Goal.values()) { goalOption ->
                        FilterChip(
                            selected = goal == goalOption.name,
                            onClick = { goal = goalOption.name },
                            label = { Text(goalOption.value) }
                        )
                    }
                }
            }
        }

        // Activity Level Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Activity Level",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                ActivityLevel.entries.forEach { level ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedActivityLevel = level
                                activityLevel = level.name
                            },
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = if (activityLevel == level.name)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "${level.emoji} ${level.value}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = level.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (activityLevel == level.name) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Save Changes Button
        Button(
            onClick = {
                val updatedData = UserDataEntity(
                    id = viewModel.data?.id ?: 0,
                    userName = userName,
                    userWeight = weight.toIntOrNull() ?: 0,
                    userHeight = height.toIntOrNull() ?: 0,
                    userWeightingUnit = weightUnit,
                    userGoal = goal,
                    userGender = gender,
                    userAge = age.toIntOrNull() ?: 0,
                    userActivityLevel = activityLevel
                )
                viewModel.updateUserDetails(updatedData)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Save Changes",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}