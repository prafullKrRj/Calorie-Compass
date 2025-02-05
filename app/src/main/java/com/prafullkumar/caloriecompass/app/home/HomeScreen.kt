package com.prafullkumar.caloriecompass.app.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.prafullkumar.caloriecompass.R

enum class Parameters(
    val value: String,
    val description: String,
    @DrawableRes val image: Int
) {
    BMI(
        "BMI",
        image = R.drawable.bmi,
        description = "Measures body fat based on height and weight."
    ),
    BMR(
        "BMR",
        image = R.drawable.bmr,
        description = "Calories burned at rest for basic body functions."
    ),
    RMR(
        "RMR",
        image = R.drawable.rmr,
        description = "Energy required to maintain vital bodily functions."
    ),
    IBW(
        "IBW",
        image = R.drawable.bmi,
        description = "Ideal body weight based on height and gender."
    )
}

class HomeViewModel : ViewModel()

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    Scaffold(Modifier.systemBarsPadding()) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
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

@Composable
fun EnergyIntakeDetails(viewModel: HomeViewModel, navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth()) {
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
            IconButton(onClick = {}, modifier = Modifier.weight(.1f)) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
fun MacronutrientDetailButton(viewModel: HomeViewModel, navController: NavController) {
    OutlinedCard(
        onClick = {}, modifier = Modifier
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
                        text = "Protein, Carbs, Fats",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

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
                Column {
                    Text(
                        text = it.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = it.description,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

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
        MacroNutrientItem(
            nutrient = "Protein",
            value = "120g"
        )
        MacroNutrientItem(
            nutrient = "Carbs",
            value = "200g"
        )
        MacroNutrientItem(
            nutrient = "Fats",
            value = "60g"
        )
    }
    HorizontalDivider(Modifier.fillMaxWidth())
}

@Composable
fun MacroNutrientItem(
    modifier: Modifier = Modifier,
    nutrient: String,
    value: String,
) {
    Column(modifier) {
        Text(
            text = nutrient,
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TdeeSection(viewModel: HomeViewModel) {
    Text(
        "Total Daily Energy Expenditure",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("🔥 ", fontSize = 32.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = AnnotatedString(
                text = "2892 ",
                spanStyle = SpanStyle(
                    fontSize = 34.sp,
                )
            ) + AnnotatedString(
                text = "Kcal", spanStyle = SpanStyle(
                    fontSize = 18.sp,
                )
            ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        text = "Calories per day",
        color = Color.Gray,
        style = MaterialTheme.typography.bodySmall
    )
}
