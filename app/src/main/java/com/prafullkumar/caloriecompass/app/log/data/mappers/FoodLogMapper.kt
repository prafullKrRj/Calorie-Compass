package com.prafullkumar.caloriecompass.app.log.data.mappers

import com.prafullkumar.caloriecompass.app.log.data.local.FoodLogEntity
import com.prafullkumar.caloriecompass.app.log.domain.model.FoodLog
import com.prafullkumar.caloriecompass.common.data.mapper.BaseMapper

class FoodLogMapper : BaseMapper<FoodLogEntity, FoodLog> {
    override fun mapToEntity(domain: FoodLog): FoodLogEntity {
        return FoodLogEntity(
            id = domain.id,
            date = domain.date,
            mealType = domain.mealType,
            foodName = domain.foodName,
            calories = domain.calories,
            protein = domain.protein ?: 0.0,
            carbs = domain.carbs ?: 0.0,
            fats = domain.fats ?: 0.0
        )
    }

    override fun mapToDomain(entity: FoodLogEntity): FoodLog {
        return FoodLog(
            id = entity.id,
            date = entity.date,
            mealType = entity.mealType,
            foodName = entity.foodName,
            calories = entity.calories,
            protein = entity.protein,
            carbs = entity.carbs,
            fats = entity.fats
        )
    }
}