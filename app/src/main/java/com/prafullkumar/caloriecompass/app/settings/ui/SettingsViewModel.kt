package com.prafullkumar.caloriecompass.app.settings.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.caloriecompass.app.settings.domain.SettingsRepository
import com.prafullkumar.caloriecompass.common.domain.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel class for managing user settings.
 *
 * @property settingsRepository Repository for accessing and updating user settings.
 * @property context Context for displaying Toast messages.
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val context: Context
) : ViewModel() {

    // Mutable state to hold user data
    var data by mutableStateOf<UserData?>(null)

    // Initialize the ViewModel by fetching user information
    init {
        getUserInfo()
    }

    /**
     * Fetches user information from the repository and updates the state.
     */
    fun getUserInfo() {
        viewModelScope.launch {
            data = settingsRepository.getUserDetails().first()
        }
    }

    /**
     * Updates user details in the repository and shows a Toast message upon success.
     *
     * @param updatedData The updated user data to be saved.
     */
    fun updateUserDetails(updatedData: UserData) {
        viewModelScope.launch {
            settingsRepository.updateUserDetails(updatedData)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Details Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}