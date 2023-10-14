package be.christiano.portfolio.app.ui.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class MainViewModel(
    dataStore: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        dataStore.userPrefs.onEach {
            _state.update { state ->
                state.copy(displayMode = it.displayMode, dynamicModeEnabled = it.dynamicEnabled)
            }
        }.launchIn(viewModelScope)
    }
}

data class MainState(
    val displayMode: Int = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
    val dynamicModeEnabled: Boolean = true
)
