package be.cbconnectit.portfolio.app.ui.main.introduction

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.enums.Social
import be.cbconnectit.portfolio.app.domain.model.Link
import be.cbconnectit.portfolio.app.domain.repository.ExperienceRepository
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
import be.cbconnectit.portfolio.app.domain.repository.TestimonialRepository
import be.cbconnectit.portfolio.app.domain.repository.WorkRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.Month

class IntroductionViewModel(
    private val serviceRepo: ServiceRepository,
    private val experienceRepo: ExperienceRepository,
    private val workRepository: WorkRepository,
    private val testimonialRepository: TestimonialRepository
) : BaseComposeViewModel(), IntroductionContract {

    private val _state = MutableStateFlow(getInitialState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<IntroductionContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        fetchAllData()

        serviceRepo.findAllServices().onEach { services ->
            _state.update { it.copy(services = services) }
        }.launchIn(viewModelScope)

        experienceRepo.findAllExperiences().onEach { experiences ->
            _state.update { it.copy(experiences = experiences) }
        }.launchIn(viewModelScope)

        workRepository.findAllWorks().onEach { works ->
            _state.update {
                it.copy(projects = works, selectedProject = it.selectedProject ?: works.firstOrNull())
            }
        }.launchIn(viewModelScope)

        testimonialRepository.findAllTestimonials().onEach { testimonials ->
            _state.update { it.copy(testimonials = testimonials) }
        }.launchIn(viewModelScope)
    }

    private fun getInitialState() = IntroductionContract.State(
        socialLinks = Social.entries.map { Link(type = it.type, url = it.link) },
        experienceInYears = getUpdateExperienceInYears()
    )

    override fun sendIntent(event: IntroductionContract.Intent) = viewModelScope.launch {
        when (event) {
            is IntroductionContract.Intent.OpenSocialLink -> emitEffect(IntroductionContract.Effect.OpenSocialLink(event.link))
            is IntroductionContract.Intent.OpenMailClient -> emitEffect(IntroductionContract.Effect.OpenMailClient)
            is IntroductionContract.Intent.OpenServiceList -> emitEffect(IntroductionContract.Effect.OpenServiceList)
            is IntroductionContract.Intent.OpenServiceDetail -> emitEffect(IntroductionContract.Effect.OpenServiceDetail(event.serviceId))
            is IntroductionContract.Intent.OpenPortfolioList -> emitEffect(IntroductionContract.Effect.OpenPortfolio)
            is IntroductionContract.Intent.OpenTestimonialsList -> showSnackbar("In Development!")
            is IntroductionContract.Intent.OpenExperiencesList -> emitEffect(IntroductionContract.Effect.OpenExperienceList)
            is IntroductionContract.Intent.UpdateSelectedWork -> _state.update { it.copy(selectedProject = event.work) }
            is IntroductionContract.Intent.RefreshData -> fetchAllData(isRefreshing = true)
        }
    }

    override fun emitEffect(effect: IntroductionContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (IntroductionContract.State) -> IntroductionContract.State) {
        _state.update(block)
    }

    private fun fetchAllData(isRefreshing: Boolean = false) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, isRefreshing = isRefreshing) }

        val servicesAsync = async { serviceRepo.fetchAllServices() }
        val experiencesAsync = async { experienceRepo.fetchAllExperiences() }
        val worksAsync = async { workRepository.fetchAllWorks() }
        val testimonialsAsync = async { testimonialRepository.fetchAllTestimonials() }

        val services = servicesAsync.await()
        val experiences = experiencesAsync.await()
        val works = worksAsync.await()
        val testimonials = testimonialsAsync.await()

        val calls = listOf(services, experiences, works, testimonials)
        if (calls.any { it.isFailure }) {
            calls.first { it.isFailure }.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        _state.update { it.copy(isLoading = false, isRefreshing = false) }
    }

    private fun getUpdateExperienceInYears(): Int {
        val startDate = LocalDate.of(2017, Month.NOVEMBER, 1)
        val currentDate = LocalDate.now()
        val yearsBetween = Duration.between(startDate.atStartOfDay(), currentDate.atStartOfDay()).toDays() / 365

        return yearsBetween.toInt()
    }
}

