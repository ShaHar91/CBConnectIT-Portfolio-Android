package be.christiano.portfolio.app.ui.landing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LandingViewModel(
    private val dataStore: UserPreferences
) : ViewModel() {

    private val _isGettingData = mutableStateOf(true)
    val isGettingData: State<Boolean> = _isGettingData

    private val _state = MutableStateFlow(LandingState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<LandingUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            val layoutSystem = dataStore.layoutSystem.firstOrNull()
            _state.update { it.copy(currentLayoutSystem = layoutSystem) }
            _isGettingData.value = false
        }
    }

    fun onEvent(event: LandingEvent) = viewModelScope.launch {
        when (event) {
            is LandingEvent.ChangeSelectedLayoutSystem -> {
                _state.update { it.copy(selectedLayoutSystem = event.layoutSystem) }
            }

            is LandingEvent.UpdateLayoutSystem -> {
                _state.value.selectedLayoutSystem?.let { dataStore.changeLayoutSystem(it) }
                _eventFlow.send(LandingUiEvent.NavigateToNextScreen)
            }
        }
    }
}