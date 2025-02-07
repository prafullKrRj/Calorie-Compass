package com.prafullkumar.caloriecompass.app.settings.data

import com.prafullkumar.caloriecompass.app.settings.domain.SettingsRepository
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntity
import com.prafullkumar.caloriecompass.common.data.local.UserDataEntityDao

class SettingsRepositoryImpl(
    private val dao: UserDataEntityDao
) : SettingsRepository {
    override suspend fun updateUserDetails(userDataEntity: UserDataEntity) {
        dao.insertUserData(userDataEntity)
    }

    override suspend fun getUserDetails() = dao.getUserData()
}