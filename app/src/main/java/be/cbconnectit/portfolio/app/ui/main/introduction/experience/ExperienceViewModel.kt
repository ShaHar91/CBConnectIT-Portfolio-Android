package be.cbconnectit.portfolio.app.ui.main.introduction.experience

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.repository.ExperienceRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExperienceViewModel(
    private val experienceRepository: ExperienceRepository
) : BaseComposeViewModel(), ExperienceContract {

    private val _state = MutableStateFlow(ExperienceContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExperienceContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        fetchExperienceData()

        experienceRepository.findAllExperiences().onEach { experiences ->
            updateState { it.copy(experiences = experiences) }
        }.launchIn(viewModelScope)
    }

    override fun sendIntent(intent: ExperienceContract.Intent) = viewModelScope.launch {
        when (intent) {
            is ExperienceContract.Intent.RefreshData -> fetchExperienceData(true)
        }
    }

    override fun emitEffect(effect: ExperienceContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (ExperienceContract.State) -> ExperienceContract.State) {
        _state.update(block)
    }


    private fun fetchExperienceData(isRefreshing: Boolean = false) = viewModelScope.launch {
        updateState { it.copy(isLoading = true, isRefreshing = isRefreshing) }

        val call = experienceRepository.fetchAllExperiences()
        if (call.isFailure) {
            call.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        updateState { it.copy(isLoading = false, isRefreshing = false) }
    }
}
