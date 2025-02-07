package com.prafullkumar.caloriecompass.app.home.data

import com.prafullkumar.caloriecompass.app.home.domain.model.TDEEData
import com.prafullkumar.caloriecompass.app.home.domain.repository.HomeRepository
import com.prafullkumar.caloriecompass.app.onBoarding.ui.ActivityLevel
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Gender
import com.prafullkumar.caloriecompass.app.onBoarding.ui.Goal
import com.prafullkumar.caloriecompass.app.onBoarding.ui.WeighingUnit
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntityDao
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Implementation of the HomeRepository interface.
 *
 * @property dao Data Access Object for user data.
 */
class HomeRepositoryImpl(
    private val dao: UserDataEntityDao
) : HomeRepository {

    /**
     * Retrieves the Total Daily Energy Expenditure (TDEE) data for the user.
     *
     * @return TDEEData containing various metabolic and nutritional information.
     */
    override suspend fun getTdeeData(): TDEEData {
        val userInfo = dao.getUserData().first()
        val bmr = calculateBMR(userInfo)
        val tdee = calculateTDEE(userInfo, bmr)
        val proteinIntake = calculateProteinIntake(userInfo, tdee)
        val fatIntake = calculateFatIntake(userInfo, tdee)
        val carbIntake = calculateCarbIntake(tdee, proteinIntake, fatIntake)

        return TDEEData(
            tdee = tdee,
            bmr = bmr,
            bmi = calculateBMI(userInfo),
            rmr = calculateRMR(userInfo),
            ibw = calculateIBW(userInfo),
            goal = Goal.valueOf(userInfo.userGoal),
            activityLevel = ActivityLevel.valueOf(userInfo.userActivityLevel),
            protein = proteinIntake,
            fat = fatIntake,
            carbs = carbIntake
        )
    }

    /**
     * Calculates the Basal Metabolic Rate (BMR) using the Mifflin-St Jeor Equation.
     *
     * @param user User data entity.
     * @return BMR value.
     */
    private fun calculateBMR(user: UserDataEntity): Double {
        val weightKg = getWeightKg(user)
        return if (user.userGender == Gender.MALE.name) {
            10 * weightKg + 6.25 * user.userHeight - 5 * user.userAge + 5
        } else {
            10 * weightKg + 6.25 * user.userHeight - 5 * user.userAge - 161
        }
    }

    /**
     * Calculates the Resting Metabolic Rate (RMR), which is similar to BMR.
     *
     * @param user User data entity.
     * @return RMR value.
     */
    private fun calculateRMR(user: UserDataEntity): Double {
        return calculateBMR(user)
    }

    /**
     * Calculates the Total Daily Energy Expenditure (TDEE) based on BMR and activity level.
     *
     * @param user User data entity.
     * @param bmr Basal Metabolic Rate.
     * @return TDEE value.
     */
    private fun calculateTDEE(user: UserDataEntity, bmr: Double): Double {
        val activityMultiplier = when (ActivityLevel.valueOf(user.userActivityLevel)) {
            ActivityLevel.SEDENTARY -> 1.2
            ActivityLevel.LIGHTLY_ACTIVE -> 1.375
            ActivityLevel.MODERATELY_ACTIVE -> 1.55
            ActivityLevel.VERY_ACTIVE -> 1.725
            ActivityLevel.SUPER_ACTIVE -> 1.9
        }
        return roundToTwoDecimals(bmr * activityMultiplier)
    }

    /**
     * Calculates the Body Mass Index (BMI).
     *
     * @param user User data entity.
     * @return BMI value.
     */
    private fun calculateBMI(user: UserDataEntity): Double {
        val weightKg = getWeightKg(user)
        val heightM = user.userHeight / 100.0
        return roundToTwoDecimals(weightKg / (heightM * heightM))
    }

    /**
     * Calculates the Ideal Body Weight (IBW) using the Hamwi Method.
     *
     * @param user User data entity.
     * @return IBW value.
     */
    private fun calculateIBW(user: UserDataEntity): Double {
        val heightInches = user.userHeight * 0.393701 // Convert cm to inches
        return if (user.userGender == Gender.MALE.name) {
            roundToTwoDecimals(50 + 2.3 * (heightInches - 60))
        } else {
            roundToTwoDecimals(45.5 + 2.3 * (heightInches - 60))
        }
    }

    /**
     * Calculates the daily protein intake based on the user's goal.
     *
     * @param user User data entity.
     * @param tdee Total Daily Energy Expenditure.
     * @return Protein intake in grams.
     */
    private fun calculateProteinIntake(user: UserDataEntity, tdee: Double): Double {
        val weightKg = getWeightKg(user)
        return when (Goal.valueOf(user.userGoal)) {
            Goal.LOSE_WEIGHT -> roundToTwoDecimals(weightKg * 2.2) // Higher for muscle retention
            Goal.GAIN_WEIGHT -> roundToTwoDecimals(weightKg * 1.8)
            Goal.MAINTAIN_WEIGHT -> roundToTwoDecimals(weightKg * 1.6)
        }
    }

    /**
     * Calculates the daily fat intake based on the user's goal.
     *
     * @param user User data entity.
     * @param tdee Total Daily Energy Expenditure.
     * @return Fat intake in grams.
     */
    private fun calculateFatIntake(user: UserDataEntity, tdee: Double): Double {
        val fatCalories = when (Goal.valueOf(user.userGoal)) {
            Goal.LOSE_WEIGHT -> tdee * 0.20 // Lower fat intake for weight loss
            Goal.GAIN_WEIGHT -> tdee * 0.35
            Goal.MAINTAIN_WEIGHT -> tdee * 0.25
        }
        return roundToTwoDecimals(fatCalories / 9) // 1g of fat = 9 kcal
    }

    /**
     * Calculates the daily carbohydrate intake based on remaining calories after protein and fat.
     *
     * @param tdee Total Daily Energy Expenditure.
     * @param protein Protein intake in grams.
     * @param fat Fat intake in grams.
     * @return Carbohydrate intake in grams.
     */
    private fun calculateCarbIntake(tdee: Double, protein: Double, fat: Double): Double {
        val proteinCalories = protein * 4 // 1g protein = 4 kcal
        val fatCalories = fat * 9
        val remainingCalories = tdee - (proteinCalories + fatCalories)
        return roundToTwoDecimals(remainingCalories / 4) // 1g carb = 4 kcal
    }

    /**
     * Converts the user's weight to kilograms if stored in pounds.
     *
     * @param user User data entity.
     * @return Weight in kilograms.
     */
    private fun getWeightKg(user: UserDataEntity): Double {
        return if (user.userWeightingUnit == WeighingUnit.KG.name) {
            user.userWeight.toDouble()
        } else {
            roundToTwoDecimals(user.userWeight * 0.453592) // Convert lbs to kg
        }
    }

    /**
     * Utility function to round a value to two decimal places.
     *
     * @param value The value to be rounded.
     * @return The rounded value.
     */
    private fun roundToTwoDecimals(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}