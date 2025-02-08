package com.prafullkumar.caloriecompass.common.data.mapper

import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity
import com.prafullkumar.caloriecompass.common.domain.UserData

class UserMapper : BaseMapper<UserDataEntity, UserData> {
    override fun mapToEntity(domain: UserData): UserDataEntity {
        return UserDataEntity(
            id = domain.id,
            userName = domain.userName,
            userWeight = domain.userWeight,
            userHeight = domain.userHeight,
            userWeightingUnit = domain.userWeightingUnit,
            userGoal = domain.userGoal,
            userGender = domain.userGender,
            userAge = domain.userAge,
            userActivityLevel = domain.userActivityLevel
        )
    }

    override fun mapToDomain(entity: UserDataEntity): UserData {
        return UserData(
            id = entity.id,
            userName = entity.userName,
            userWeight = entity.userWeight,
            userHeight = entity.userHeight,
            userWeightingUnit = entity.userWeightingUnit,
            userGoal = entity.userGoal,
            userGender = entity.userGender,
            userAge = entity.userAge,
            userActivityLevel = entity.userActivityLevel
        )
    }
}