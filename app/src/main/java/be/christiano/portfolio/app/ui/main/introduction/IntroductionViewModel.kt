package be.christiano.portfolio.app.ui.main.introduction

import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.model.Service
import be.christiano.portfolio.app.domain.enums.Social
import be.christiano.portfolio.app.domain.model.Testimonial
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.repository.ExperienceRepository
import be.christiano.portfolio.app.domain.repository.ServiceRepository
import be.christiano.portfolio.app.domain.repository.TestimonialRepository
import be.christiano.portfolio.app.domain.repository.WorkRepository
import be.christiano.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class IntroductionViewModel(
    private val serviceRepo: ServiceRepository,
    private val experienceRepo: ExperienceRepository,
    private val workRepository: WorkRepository,
    private val testimonialRepository: TestimonialRepository
) : BaseComposeViewModel() {

    private val _state = MutableStateFlow(
        IntroductionState(experienceInYears = getUpdateExperienceInYears())
    )
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<IntroductionUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
    }

    private fun fetchAllServices() {
        viewModelScope.launch {
            val services = serviceRepo.fetchAllServices()
            _state.update { it.copy(services = services.data ?: emptyList()) }
        }
    }

    private fun fetchAllExperiences() {
        viewModelScope.launch {
            val experiences = experienceRepo.fetchAllExperiences()
            _state.update { it.copy(experiences = experiences.data ?: emptyList()) }
        }
    }

    private fun fetchAllWorks() {
        viewModelScope.launch {
            val works = workRepository.fetchAllWorks()
            _state.update { it.copy(projects = works.data ?: emptyList()) }
        }
    }

    private fun fetchAllTestimonials() {
        viewModelScope.launch {
            val testimonials = testimonialRepository.fetchAllTestimonials()
            _state.update { it.copy(testimonials = testimonials.data ?: emptyList()) }
        }
    }

    private fun getUpdateExperienceInYears(): Int {
        //TODO: maybe try to simplify this?
        val started = LocalDateTime.of(2017, Month.NOVEMBER, 1, 0, 0).toInstant(ZoneOffset.UTC)
        val current = LocalDateTime.now().toInstant(ZoneOffset.UTC)

        val difference = current.minusMillis(started.toEpochMilli()).toEpochMilli()

        return (difference.toDuration(DurationUnit.MILLISECONDS).inWholeDays / 365).toInt()
    }

    fun onEvent(event: IntroductionEvent) = viewModelScope.launch {
        when (event) {
            is IntroductionEvent.OpenSocialLink -> _eventFlow.send(IntroductionUiEvent.OpenSocialLink(event.social))
            is IntroductionEvent.OpenMailClient -> {
                //TODO: remove all data fetches from this place!
                //_eventFlow.send(IntroductionUiEvent.OpenMailClient)
                fetchAllServices()
                fetchAllExperiences()
                fetchAllWorks()
                fetchAllTestimonials()
            }

            is IntroductionEvent.OpenServiceList -> showSnackbar("In Development!")
            is IntroductionEvent.OpenPortfolioList -> showSnackbar("In Development!")
            is IntroductionEvent.OpenTestimonialsList -> showSnackbar("In Development!")
            is IntroductionEvent.OpenExperiencesList -> showSnackbar("In Development!")
        }
    }
}

sealed class IntroductionEvent {
    data class OpenSocialLink(val social: Social) : IntroductionEvent()
    data object OpenMailClient : IntroductionEvent()
    data object OpenServiceList : IntroductionEvent()
    data object OpenPortfolioList : IntroductionEvent()
    data object OpenTestimonialsList : IntroductionEvent()
    data object OpenExperiencesList : IntroductionEvent()
}

data class IntroductionState(
    val isLoading: Boolean = false,
    val experienceInYears: Int = 0,
    val services: List<Service> = emptyList(),
    val projects: List<Work> = emptyList(),
    val testimonials: List<Testimonial> = emptyList(),
    val experiences: List<Experience> = emptyList()
)

sealed class IntroductionUiEvent {
    data class OpenSocialLink(val social: Social) : IntroductionUiEvent()
    data object OpenMailClient : IntroductionUiEvent()
}