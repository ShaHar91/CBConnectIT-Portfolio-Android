package be.cbconnectit.portfolio.app.ui.main.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.BuildConfig
import be.cbconnectit.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: UserPreferences
) : ViewModel(), SettingsContract {

    private val _state = MutableStateFlow(SettingsContract.State(appVersion = "v${BuildConfig.VERSION_NAME}"))
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            val userPrefs = dataStore.userPrefs.first()
            updateState {
                it.copy(
                    dynamicModeEnabled = userPrefs.dynamicEnabled,
                    selectedDisplayMode = AppCompatDelegate.getDefaultNightMode(),
                    currentLayoutSystem = userPrefs.layoutSystem,
                    selectedLayoutSystem = userPrefs.layoutSystem
                )
            }
        }
    }

    override fun sendIntent(intent: SettingsContract.Intent) = viewModelScope.launch {
        when (intent) {
            is SettingsContract.Intent.ChangeDisplayMode -> {
                AppCompatDelegate.setDefaultNightMode(intent.displayMode)
                dataStore.changeDisplayMode(intent.displayMode)
                updateState { it.copy(selectedDisplayMode = intent.displayMode) }
            }

            is SettingsContract.Intent.ChangeSelectedLayoutSystem -> {
                // Don't do anything when the same item is being selected
                if (intent.layoutSystem == _state.value.selectedLayoutSystem) {
                    updateState { it.copy(selectedLayoutSystemExpanded = false) }
                    return@launch
                }

                updateState { it.copy(selectedLayoutSystem = intent.layoutSystem, showConfirmationDialog = true) }
            }

            is SettingsContract.Intent.PersistSelectedLayoutSystem -> {
                updateState { it.copy(selectedLayoutSystemExpanded = false, showConfirmationDialog = false) }
                _state.value.selectedLayoutSystem?.let { dataStore.changeLayoutSystem(it) }
                emitEffect(SettingsContract.Effect.RestartApplication)
            }

            is SettingsContract.Intent.ResetSelectedLayoutSystem -> {
                updateState { it.copy(selectedLayoutSystem = it.currentLayoutSystem, selectedLayoutSystemExpanded = false, showConfirmationDialog = false) }
            }

            is SettingsContract.Intent.ChangeDynamicMode -> {
                dataStore.changeDynamicEnabled(intent.dynamicModeEnabled)
                updateState { it.copy(dynamicModeEnabled = intent.dynamicModeEnabled) }
            }

            is SettingsContract.Intent.UpdateSelectedLayoutSystemExpanded -> {
                updateState { it.copy(selectedLayoutSystemExpanded = intent.expanded) }
            }

            is SettingsContract.Intent.ShowUnsupportedDynamicFeatureDialog -> {
                updateState { it.copy(showUnsupportedDynamicFeatureDialog = intent.shown) }
            }
        }
    }

    override fun emitEffect(effect: SettingsContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (SettingsContract.State) -> SettingsContract.State) {
        _state.update(block)
    }
}
