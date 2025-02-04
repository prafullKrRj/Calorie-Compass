package com.prafullkumar.caloriecompass.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.prafullkumar.caloriecompass.MainRoutes
import com.prafullkumar.caloriecompass.R

@Composable
fun OnBoardingFormScreen(viewModel: OnBoardingViewModel, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally
    ) {
        Column(
            Modifier
                .weight(.8f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            Image(
                modifier = Modifier.padding(top = 16.dp),
                painter = painterResource(R.drawable.form_image),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit
            )
            Text(
                "Tell Us About Yourself",
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            // gender selection
            GenderSection(viewModel)
            NameTextField(viewModel)
            AgeSection(viewModel)
            WeightSection(viewModel)
            GoalSelection(viewModel)
        }
        NextButton(navController)
    }

}

@Composable
fun AgeSection(viewModel: OnBoardingViewModel) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "What's your age?",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(.5f)
        )
        FormField(
            modifier = Modifier.weight(.5f),
            value = viewModel.uiState.age,
            label = "25",
            suffix = "years"
        ) {
            viewModel.uiState = viewModel.uiState.copy(age = it)
        }
    }
}

@Composable
fun WeightSection(viewModel: OnBoardingViewModel) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "What's your weight?",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,

            modifier = Modifier.weight(.5f)
        )
        FormField(
            modifier = Modifier.weight(.5f),
            value = viewModel.uiState.weight,
            label = "99",
            suffix = "kg"
        ) {
            viewModel.uiState = viewModel.uiState.copy(weight = it)
        }
    }
}

@Composable
fun NameTextField(viewModel: OnBoardingViewModel) {
    FormField(
        Modifier.fillMaxWidth(), value = viewModel.uiState.name, label = "What's your name?"
    ) {
        viewModel.uiState = viewModel.uiState.copy(
            name = it
        )
    }
}

@Composable
fun FormField(
    modifier: Modifier,
    value: String,
    label: String,
    suffix: String = "",
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(35),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            focusedBorderColor = MaterialTheme.colorScheme.surfaceBright,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceBright,
            unfocusedLabelColor = MaterialTheme.colorScheme.surfaceTint,
        ),
        label = {
            Text(label, fontSize = 18.sp, fontWeight = FontWeight.W400)
        },
        trailingIcon = {
            Text(suffix, fontSize = 18.sp, fontWeight = FontWeight.W400)
        })
}

@Composable
fun GoalSelection(viewModel: OnBoardingViewModel) {
    Text(
        "What's your aim?",
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )

    Goal.entries.forEach {
        GoalItem(goal = it, selected = viewModel.uiState.goal == it, onGoalClicked = {
            viewModel.uiState = viewModel.uiState.copy(
                goal = it
            )
        })
    }
}

@Composable
fun GoalItem(
    goal: Goal, onGoalClicked: () -> Unit = {}, selected: Boolean
) {
    InputChip(onClick = { onGoalClicked() }, selected = selected, label = {
        Text(
            goal.value,
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }, shape = RoundedCornerShape(30)
    )
}

@Composable
fun NextButton(navController: NavController) {
    Button(
        modifier = Modifier.padding(vertical = 16.dp), onClick = {
            navController.navigate(MainRoutes.Home)
        }, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.surfaceBright
        )
    ) {
        Text("Next", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 22.dp))
    }
}

@Composable
fun GenderSection(viewModel: OnBoardingViewModel) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Gender.entries.forEach {
            GenderSelectionChip(
                onGenderClicked = {
                    viewModel.uiState = viewModel.uiState.copy(
                        gender = it
                    )
                },
                selected = viewModel.uiState.gender == it,
                gender = it,
                modifier = Modifier.weight(.5f)
            )
        }
    }
}

@Composable
fun GenderSelectionChip(
    onGenderClicked: () -> Unit, gender: Gender, selected: Boolean = false, modifier: Modifier
) {
    InputChip(onClick = onGenderClicked, selected = selected, label = {
        Text(
            gender.value,
            Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }, modifier = modifier, shape = RoundedCornerShape(30))
}