package be.cbconnectit.portfolio.app.ui.landing

import be.cbconnectit.portfolio.app.domain.enums.LayoutSystem

data class LandingState(
    val currentLayoutSystem: LayoutSystem? = null,
    val selectedLayoutSystem: LayoutSystem? = null
)

sealed class LandingEvent {
    data class ChangeSelectedLayoutSystem(val layoutSystem: LayoutSystem) : LandingEvent()
    data object UpdateLayoutSystem : LandingEvent()
}

sealed class LandingUiEvent {
    data object NavigateToNextScreen : LandingUiEvent()
}
