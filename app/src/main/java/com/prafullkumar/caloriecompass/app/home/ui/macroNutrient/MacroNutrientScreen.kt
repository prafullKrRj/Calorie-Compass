package com.prafullkumar.caloriecompass.app.home.ui.macroNutrient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.R

/**
 * Composable function to display the Macro Nutrient Screen.
 *
 * @param viewModel The ViewModel for the Macro Nutrient Screen.
 * @param navController The NavController for navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MacroNutrientScreen(
    viewModel: MacroNutrientViewModel, navController: NavController
) {
    var selectedPhase by rememberSaveable { mutableStateOf(Phase.MAINTAINING) }
    val scrollState = rememberScrollState()
    Scaffold(Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text("Macro Nutrient")
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
                .padding(horizontal = 12.dp)
                .verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Phase.entries.forEach {
                    PhaseCard(modifier = Modifier
                        .weight(1f)
                        .padding(),
                        phase = it,
                        selectedPhase = selectedPhase,
                        onPhaseSelected = {
                            selectedPhase = it
                        })
                }
            }
            viewModel.macroNutrientState?.let {
                MacroNutrientDetails(it.data[selectedPhase]!!)
            }
        }
    }
}

/**
 * Composable function to display a card with macro nutrient details.
 *
 * @param modifier The modifier to be applied to the card.
 * @param carbs The carbs data to be displayed.
 * @param macroNutrient The macro nutrient data to be displayed.
 */
@Composable
fun MacroNutrientCard(
    modifier: Modifier = Modifier, carbs: Carbs, macroNutrient: MacroNutrient
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ), shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with carbs name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = carbs.name.capitalize(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_fitness_center_24),
                    contentDescription = "Macro Nutrients",
                    tint = Color(0xFF4CAF50)
                )
            }

            // Macro Nutrient Details
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MacroNutrientRow(
                    label = "Carbs",
                    value = String.format("%.2f", macroNutrient.carbs) + " gm",
                    color = Color(0xFF2196F3)
                )
                MacroNutrientRow(
                    label = "Protein",
                    value = String.format("%.2f", macroNutrient.protein) + " gm",
                    color = Color(0xFFFFC107)
                )
                MacroNutrientRow(
                    label = "Fat",
                    value = String.format("%.2f", macroNutrient.fat) + " gm",
                    color = Color(0xFFF44336)
                )
            }
        }
    }
}

/**
 * Composable function to display a row with macro nutrient details.
 *
 * @param label The label for the macro nutrient.
 * @param value The value of the macro nutrient.
 * @param color The color to be applied to the value text.
 */
@Composable
fun MacroNutrientRow(
    label: String, value: String, color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black.copy(alpha = 0.8f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Composable function to display the macro nutrient details for a specific phase.
 *
 * @param phaseData The data for the selected phase.
 */
@Composable
fun MacroNutrientDetails(
    phaseData: PhaseData
) {
    phaseData.requirements.forEach { (carbs, macroNutrient) ->
        MacroNutrientCard(
            carbs = carbs,
            macroNutrient = macroNutrient
        )
    }
}

/**
 * Composable function to display a card for selecting a phase.
 *
 * @param modifier The modifier to be applied to the card.
 * @param phase The phase to be displayed.
 * @param selectedPhase The currently selected phase.
 * @param onPhaseSelected The callback to be invoked when the phase is selected.
 */
@Composable
fun PhaseCard(
    modifier: Modifier = Modifier, phase: Phase, selectedPhase: Phase, onPhaseSelected: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onPhaseSelected,
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (phase == selectedPhase) {
                MaterialTheme.colorScheme.surfaceDim
            } else {
                MaterialTheme.colorScheme.background
            }
        ),
    ) {
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                phase.value,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}