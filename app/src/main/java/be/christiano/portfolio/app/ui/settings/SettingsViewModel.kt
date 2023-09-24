package be.christiano.portfolio.app.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.BuildConfig
import be.christiano.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            appVersion = BuildConfig.VERSION_NAME
        )
    )
    val state = _state.asStateFlow()

    fun onEvent(event: SettingsEvent) = viewModelScope.launch {
        when (event) {
            is SettingsEvent.ChangeDisplayMode -> {
                AppCompatDelegate.setDefaultNightMode(event.displayMode)
                dataStore.changeDisplayMode(event.displayMode)
            }
        }
    }
}

sealed class SettingsEvent {
    data class ChangeDisplayMode(val displayMode: Int) : SettingsEvent()
}

data class SettingsState(
    val isLoading: Boolean = false,
    val language: String = "-",
    val appVersion: String = "-"
)

sealed class SettingsUiEvent {
    object DummyUiEvent : SettingsUiEvent()
}