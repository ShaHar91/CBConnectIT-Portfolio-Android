package be.cbconnectit.portfolio.app.ui.main.introduction.portfolio

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.model.Link
import be.cbconnectit.portfolio.app.domain.model.Work
import be.cbconnectit.portfolio.app.domain.repository.WorkRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val workRepository: WorkRepository
) : BaseComposeViewModel() {

    private val _state = MutableStateFlow(PortfolioState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<PortfolioUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        fetchAllData()


        workRepository.findAllWorks().onEach { works ->
            _state.update { it.copy(projects = works) }
        }.launchIn(viewModelScope)

    }

    private fun fetchAllData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val worksAsync = async { workRepository.fetchAllWorks() }

        val works = worksAsync.await()

        val calls = listOf(works, )
        if (calls.any { it.isFailure }) {
            calls.first { it.isFailure }.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        _state.update { it.copy(isLoading = false) }
    }

    fun onEvent(event: PortfolioEvent) = viewModelScope.launch {
        when (event) {
            is PortfolioEvent.OpenSocialLink -> _eventFlow.send(PortfolioUiEvent.OpenSocialLink(event.link))
        }
    }
}

sealed class PortfolioEvent {
    data class OpenSocialLink(val link: Link) : PortfolioEvent()
}

data class PortfolioState(
    val isLoading: Boolean = false,
    val projects: List<Work> = emptyList(),
)

sealed class PortfolioUiEvent {
    data class OpenSocialLink(val link:Link) : PortfolioUiEvent()
}