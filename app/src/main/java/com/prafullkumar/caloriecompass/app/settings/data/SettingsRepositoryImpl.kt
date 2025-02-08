package com.prafullkumar.caloriecompass.app.settings.data

import com.prafullkumar.caloriecompass.app.settings.domain.SettingsRepository
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntityDao
import com.prafullkumar.caloriecompass.common.data.mapper.UserMapper
import com.prafullkumar.caloriecompass.common.domain.UserData

class SettingsRepositoryImpl(
    private val dao: UserDataEntityDao
) : SettingsRepository {
    private val userMapper = UserMapper()
    override suspend fun updateUserDetails(userDataEntity: UserData) {
        dao.insertUserData(userMapper.mapToEntity(userDataEntity.copy(id = 0)))
    }

    override suspend fun getUserDetails() = dao.getUserData().map { userMapper.mapToDomain(it) }
}