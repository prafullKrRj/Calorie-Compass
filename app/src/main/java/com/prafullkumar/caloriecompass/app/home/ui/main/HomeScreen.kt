package com.prafullkumar.caloriecompass.app.home.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.HomeRoutes
import com.prafullkumar.caloriecompass.R

/**
 * Composable function to display the Home Screen.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 * @param navController The NavController for navigation.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel, navController: NavController, innerPadding: PaddingValues
) {
    Column(Modifier
        .fillMaxSize()
        .padding(innerPadding)) {
        if (viewModel.state.loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TdeeSection(viewModel)
                }
                item {
                    MacroNutrientsDetails(viewModel)
                }
                item {
                    EnergyIntakeDetails(viewModel, navController)
                }
                item {
                    ParametersSection(viewModel)
                }
                item {
                    HorizontalDivider(Modifier.fillMaxWidth())
                }
                item {
                    MacronutrientDetailButton(viewModel, navController)
                }
            }
        }
    }
}

/**
 * Composable function to display the Energy Intake Details section.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 * @param navController The NavController for navigation.
 */
@Composable
fun EnergyIntakeDetails(viewModel: HomeViewModel, navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = {
        navController.navigate(HomeRoutes.CalorieIntakeScreen)
    }) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.food),
                contentDescription = null,
                modifier = Modifier.height(64.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = "Energy Intake Details",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(onClick = {
                navController.navigate(HomeRoutes.CalorieIntakeScreen)
            }, modifier = Modifier.weight(.1f)) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

/**
 * Composable function to display the Macronutrient Detail Button.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 * @param navController The NavController for navigation.
 */
@Composable
fun MacronutrientDetailButton(viewModel: HomeViewModel, navController: NavController) {
    OutlinedCard(
        onClick = {
            navController.navigate(HomeRoutes.MacroNutrientScreen)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.macro),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Column {
                    Text(
                        text = "Macronutrient Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "Protein, Carbs, Fats", color = Color.Gray, fontSize = 13.sp
                    )
                }
            }
            IconButton(onClick = {
                navController.navigate(HomeRoutes.MacroNutrientScreen)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

/**
 * Composable function to display the Parameters Section.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 */
@Composable
fun ParametersSection(viewModel: HomeViewModel) {
    Parameters.entries.forEach {
        OutlinedCard(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = it.image),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column() {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.name, fontWeight = FontWeight.Bold, fontSize = 17.sp
                        )
                        Text(
                            text = viewModel.state.parameters[it] ?: "",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                    Text(
                        text = it.description,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

/**
 * Composable function to display the Macro Nutrients Details section.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 */
@Composable
fun MacroNutrientsDetails(viewModel: HomeViewModel) {
    HorizontalDivider(Modifier.fillMaxWidth())
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MacroNutrients.entries.forEach {
            MacroNutrientItem(
                nutrient = it.name,
                value = viewModel.state.macroNutrients[it].toString()
            )
        }
    }
    HorizontalDivider(Modifier.fillMaxWidth())
}

/**
 * Composable function to display a single Macro Nutrient Item.
 *
 * @param modifier The Modifier to be applied to the item.
 * @param nutrient The name of the nutrient.
 * @param value The value of the nutrient.
 */
@Composable
fun MacroNutrientItem(
    modifier: Modifier = Modifier,
    nutrient: String,
    value: String,
) {
    Column(modifier) {
        Text(
            text = nutrient, color = Color.Gray, style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Composable function to display the TDEE (Total Daily Energy Expenditure) section.
 *
 * @param viewModel The ViewModel associated with the Home Screen.
 */
@Composable
fun TdeeSection(viewModel: HomeViewModel) {
    Text(
        "Total Daily Energy Expenditure",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Text("🔥 ", fontSize = 32.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = AnnotatedString(
                text = viewModel.state.tdee.toString(), spanStyle = SpanStyle(
                    fontSize = 34.sp,
                )
            ) + AnnotatedString(
                text = "Kcal", spanStyle = SpanStyle(
                    fontSize = 18.sp,
                )
            ), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold
        )
    }
    Text(
        text = "Calories per day", color = Color.Gray, style = MaterialTheme.typography.bodySmall
    )
}