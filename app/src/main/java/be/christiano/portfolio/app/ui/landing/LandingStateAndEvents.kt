package be.christiano.portfolio.app.ui.landing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import be.christiano.portfolio.app.R

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

enum class LayoutSystem(@StringRes val systemName: Int, @StringRes val description: Int, @DrawableRes val drawable: Int) {
    Compose(R.string.jetpack_compose, R.string.jetpack_compose_description, R.drawable.compose),
    Xml(R.string.xml_layout, R.string.xml_layout_description, R.drawable.xml)
}