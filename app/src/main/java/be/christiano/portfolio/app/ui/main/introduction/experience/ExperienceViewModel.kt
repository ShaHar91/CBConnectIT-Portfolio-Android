package be.christiano.portfolio.app.ui.main.introduction.experience

import androidx.lifecycle.viewModelScope
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.repository.ExperienceRepository
import be.christiano.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExperienceViewModel(
    private val experienceRepository: ExperienceRepository
): BaseComposeViewModel() {

    private val _state = MutableStateFlow(ExperienceState())
    val state = _state.asStateFlow()

    init {
        fetchExperienceData()

        experienceRepository.findAllExperiences().onEach { experiences ->
            _state.update { it.copy(experiences = experiences) }
        }.launchIn(viewModelScope)
    }

    private fun fetchExperienceData() = viewModelScope.launch{
        _state.update { it.copy(isLoading = true) }

        val call = experienceRepository.fetchAllExperiences()
        if (call.isFailure) {
            call.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        _state.update { it.copy(isLoading = false) }
    }
}

data class ExperienceState(
    val isLoading: Boolean = false,
    val experiences: List<Experience> = emptyList()
)