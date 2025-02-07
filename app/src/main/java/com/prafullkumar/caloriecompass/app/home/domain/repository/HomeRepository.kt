package com.prafullkumar.caloriecompass.app.home.domain.repository

import com.prafullkumar.caloriecompass.app.home.domain.model.TDEEData

/**
 * HomeRepository interface provides a contract for fetching TDEE (Total Daily Energy Expenditure) data.
 */
interface HomeRepository {
    /**
     * Fetches the TDEE data.
     *
     * @return TDEEData containing various health metrics.
     */
    suspend fun getTdeeData(): TDEEData
}