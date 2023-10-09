package be.christiano.portfolio.app.ui.main.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.ui.main.introduction.sections.components.Social
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class IntroductionViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        IntroductionState()
    )
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<IntroductionUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    //TODO: remove this when starting on xml!!
    val text = state.map { it.text }.asLiveData()

    init {
        updateExperienceInYears()
    }

    private fun updateExperienceInYears() {
        //TODO: maybe try to simplify this?
        val started = LocalDateTime.of(2017, Month.NOVEMBER, 1, 0, 0).toInstant(ZoneOffset.UTC)
        val current = LocalDateTime.now().toInstant(ZoneOffset.UTC)

        val difference = current.minusMillis(started.toEpochMilli()).toEpochMilli()

        val yearsExperience = difference.toDuration(DurationUnit.MILLISECONDS).inWholeDays / 365

        _state.update { it.copy(experienceInYears = yearsExperience.toInt()) }
    }

    fun onEvent(event: IntroductionEvent) = viewModelScope.launch {
        when (event) {
            is IntroductionEvent.DummyEvent -> {}
            is IntroductionEvent.OpenSocialLink -> _eventFlow.send(IntroductionUiEvent.OpenSocialLink(event.social))
            is IntroductionEvent.OpenMailClient -> _eventFlow.send(IntroductionUiEvent.OpenMailClient)
        }
    }
}

sealed class IntroductionEvent {
    data object DummyEvent : IntroductionEvent()
    data class OpenSocialLink(val social: Social) : IntroductionEvent()
    data object OpenMailClient : IntroductionEvent()
}

data class IntroductionState(
    val isLoading: Boolean = false,
    val experienceInYears: Int = 0,
    val text: String = "This is home fragment"
)

sealed class IntroductionUiEvent {
    data object DummyUiEvent : IntroductionUiEvent()
    data class OpenSocialLink(val social: Social) : IntroductionUiEvent()
    data object OpenMailClient : IntroductionUiEvent()
}