package be.christiano.portfolio.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LandingViewModel(
    private val dataStore: UserPreferences
) : ViewModel() {

    private val _isGettingData = mutableStateOf(true)
    val isGettingData: State<Boolean> = _isGettingData

    private val _state = MutableStateFlow(LandingState())

    val state = _state.asStateFlow()

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
                _state.value.selectedLayoutSystem?.let { dataStore::changeLayoutSystem }
                //TODO: navigate to the correct location!!! MainActivity or MainComposeActivity!!!
            }
        }
    }
}

data class LandingState(
    val currentLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystem: LayoutSystem? = null
)

sealed class LandingEvent {
    data class ChangeSelectedLayoutSystem(val layoutSystem: LayoutSystem) : LandingEvent()
    data object UpdateLayoutSystem : LandingEvent()
}

sealed class LandingUiEvent {
    object DummyUiEvent : LandingUiEvent()
}

enum class LayoutSystem(@StringRes val systemName: Int, @StringRes val description: Int, @DrawableRes val drawable: Int) {
    Compose(R.string.jetpack_compose, R.string.jetpack_compose_description, R.drawable.compose),
    Xml(R.string.xml_layout, R.string.xml_layout_description, R.drawable.xml)
}