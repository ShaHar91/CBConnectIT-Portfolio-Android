package be.christiano.portfolio.app.ui.landing

import be.christiano.portfolio.app.domain.enums.LayoutSystem

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
