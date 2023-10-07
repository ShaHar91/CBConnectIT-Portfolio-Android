package be.christiano.portfolio.app.ui.main.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.BuildConfig
import be.christiano.portfolio.app.data.preferences.UserPreferences
import be.christiano.portfolio.app.ui.landing.LandingUiEvent
import be.christiano.portfolio.app.ui.landing.LayoutSystem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            appVersion = "v${BuildConfig.VERSION_NAME}"
        )
    )
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<SettingsUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            val layoutSystem = dataStore.layoutSystem.firstOrNull()
            _state.update { it.copy(currentLayoutSystem = layoutSystem) }
        }
    }

    fun onEvent(event: SettingsEvent) = viewModelScope.launch {
        when (event) {
            is SettingsEvent.ChangeDisplayMode -> {
                AppCompatDelegate.setDefaultNightMode(event.displayMode)
                dataStore.changeDisplayMode(event.displayMode)
            }
            is SettingsEvent.ChangeSelectedLayoutSystem -> {
                _state.update { it.copy(selectedLayoutSystem = event.layoutSystem) }
                //TODO: show dialog to ask for confirmation from the user!!
                //TODO: when user presses continue, save the selected system to the datastore and recreate the application
                //TODO: when user presses cancel, reset the state to the current Layout system

                dataStore.changeLayoutSystem(event.layoutSystem)
                _eventFlow.send(SettingsUiEvent.RestartApplication)
            }
        }
    }
}

sealed class SettingsEvent {
    data class ChangeDisplayMode(val displayMode: Int) : SettingsEvent()
    data class ChangeSelectedLayoutSystem(val layoutSystem: LayoutSystem) : SettingsEvent()
}

data class SettingsState(
    val isLoading: Boolean = false,
    val currentLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystem: LayoutSystem? = null,
    val language: String = "-",
    val appVersion: String = "-"
)

sealed class SettingsUiEvent {
    data object RestartApplication : SettingsUiEvent()
}