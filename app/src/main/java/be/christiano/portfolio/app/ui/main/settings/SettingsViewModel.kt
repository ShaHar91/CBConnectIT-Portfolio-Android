package be.christiano.portfolio.app.ui.main.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.BuildConfig
import be.christiano.portfolio.app.data.preferences.UserPreferences
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
            _state.update { it.copy(currentLayoutSystem = layoutSystem, selectedLayoutSystem = layoutSystem) }
        }
    }

    fun onEvent(event: SettingsEvent) = viewModelScope.launch {
        when (event) {
            is SettingsEvent.ChangeDisplayMode -> {
                AppCompatDelegate.setDefaultNightMode(event.displayMode)
                dataStore.changeDisplayMode(event.displayMode)
            }

            is SettingsEvent.ChangeSelectedLayoutSystem -> {
                // Don't do anything when the same item is being selected
                if (event.layoutSystem == _state.value.selectedLayoutSystem) {
                    _state.update { it.copy(selectedLayoutSystemExpanded = false) }
                    return@launch
                }

                _state.update { it.copy(selectedLayoutSystem = event.layoutSystem, showConfirmationDialog = true) }
            }

            is SettingsEvent.PersistSelectedLayoutSystem -> {
                _state.update { it.copy(selectedLayoutSystemExpanded = false, showConfirmationDialog = false) }
                _state.value.selectedLayoutSystem?.let { dataStore.changeLayoutSystem(it) }
                _eventFlow.send(SettingsUiEvent.RestartApplication)
            }

            is SettingsEvent.ResetSelectedLayoutSystem -> {
                _state.update { it.copy(selectedLayoutSystem = it.currentLayoutSystem, selectedLayoutSystemExpanded = false, showConfirmationDialog = false) }
            }

            is SettingsEvent.ChangeDynamicMode -> {
                _state.update { it.copy(dynamicModeEnabled = event.dynamicModeEnabled) }
            }

            is SettingsEvent.UpdateSelectedLayoutSystemExpanded -> {
                _state.update { it.copy(selectedLayoutSystemExpanded = event.expanded) }
            }
        }
    }
}

sealed class SettingsEvent {
    data class ChangeDisplayMode(val displayMode: Int) : SettingsEvent()
    data class ChangeSelectedLayoutSystem(val layoutSystem: LayoutSystem) : SettingsEvent()
    data object PersistSelectedLayoutSystem : SettingsEvent()
    data object ResetSelectedLayoutSystem : SettingsEvent()
    data class ChangeDynamicMode(val dynamicModeEnabled: Boolean) : SettingsEvent()
    data class UpdateSelectedLayoutSystemExpanded(val expanded: Boolean) : SettingsEvent()
}

data class SettingsState(
    val isLoading: Boolean = false,
    val currentLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystemExpanded: Boolean = false,
    val dynamicModeEnabled: Boolean = false,
    val language: String = "-",
    val appVersion: String = "-",
    val showConfirmationDialog: Boolean = false
)

sealed class SettingsUiEvent {
    data object RestartApplication : SettingsUiEvent()
}