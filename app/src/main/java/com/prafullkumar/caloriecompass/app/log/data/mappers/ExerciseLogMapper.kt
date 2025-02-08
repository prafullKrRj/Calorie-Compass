package com.prafullkumar.caloriecompass.app.log.data.mappers

import com.prafullkumar.caloriecompass.app.log.data.local.ExerciseLogEntity
import com.prafullkumar.caloriecompass.app.log.domain.model.ExerciseLog
import com.prafullkumar.caloriecompass.common.data.mapper.BaseMapper

class ExerciseLogMapper : BaseMapper<ExerciseLogEntity, ExerciseLog> {
    override fun mapToEntity(domain: ExerciseLog): ExerciseLogEntity {
        return ExerciseLogEntity(
            id = domain.id,
            date = domain.date,
            exerciseType = domain.exerciseType,
            duration = domain.duration,
            caloriesBurned = domain.caloriesBurned
        )
    }

    override fun mapToDomain(entity: ExerciseLogEntity): ExerciseLog {
        return ExerciseLog(
            id = entity.id,
            date = entity.date,
            exerciseType = entity.exerciseType,
            duration = entity.duration,
            caloriesBurned = entity.caloriesBurned
        )
    }
}