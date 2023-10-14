package be.christiano.portfolio.app.ui.main.settings

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.BuildConfig
import be.christiano.portfolio.app.data.models.LayoutSystem
import be.christiano.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
            val userPrefs = dataStore.userPrefs.first()
            _state.update {
                it.copy(
                    dynamicModeEnabled = userPrefs.dynamicEnabled,
                    selectedDisplayMode = AppCompatDelegate.getDefaultNightMode(),
                    currentLayoutSystem = userPrefs.layoutSystem,
                    selectedLayoutSystem = userPrefs.layoutSystem
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) = viewModelScope.launch {
        when (event) {
            is SettingsEvent.ChangeDisplayMode -> {
                AppCompatDelegate.setDefaultNightMode(event.displayMode)
                dataStore.changeDisplayMode(event.displayMode)
                _state.update { it.copy(selectedDisplayMode = event.displayMode) }
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
                dataStore.changeDynamicEnabled(event.dynamicModeEnabled)
                _state.update { it.copy(dynamicModeEnabled = event.dynamicModeEnabled) }
            }

            is SettingsEvent.UpdateSelectedLayoutSystemExpanded -> {
                _state.update { it.copy(selectedLayoutSystemExpanded = event.expanded) }
            }

            is SettingsEvent.ShowUnsupportedDynamicFeatureDialog -> {
                _state.update { it.copy(ShowUnsupportedDynamicFeatureDialog = event.shown) }
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
    data class ShowUnsupportedDynamicFeatureDialog(val shown: Boolean) : SettingsEvent()
}

data class SettingsState(
    val isLoading: Boolean = false,
    val selectedDisplayMode: Int = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
    val currentLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystemExpanded: Boolean = false,
    val dynamicModeEnabled: Boolean = true,
    val language: String = "-",
    val appVersion: String = "-",
    val showConfirmationDialog: Boolean = false,
    val ShowUnsupportedDynamicFeatureDialog: Boolean = false
) {
    val hasDynamicSupport = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

sealed class SettingsUiEvent {
    data object RestartApplication : SettingsUiEvent()
}