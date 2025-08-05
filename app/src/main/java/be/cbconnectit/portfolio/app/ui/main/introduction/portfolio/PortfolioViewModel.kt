package be.cbconnectit.portfolio.app.ui.main.introduction.portfolio

import androidx.lifecycle.viewModelScope
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

class PortfolioViewModel(
    private val workRepository: WorkRepository,
    tagIds: Array<String>
) : BaseComposeViewModel(), PortfolioContract {

    private val _state = MutableStateFlow(PortfolioContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PortfolioContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        fetchAllData()

        workRepository.findAllWorks().onEach { works ->
            _state.update { it.copy(projects = works) }
        }.launchIn(viewModelScope)

    }

    override fun sendIntent(intent: PortfolioContract.Intent) = viewModelScope.launch {
        when (intent) {
            is PortfolioContract.Intent.OpenSocialLink -> emitEffect(PortfolioContract.Effect.OpenSocialLink(intent.link))
            is PortfolioContract.Intent.RefreshData -> fetchAllData(true)
        }
    }

    override fun emitEffect(effect: PortfolioContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (PortfolioContract.State) -> PortfolioContract.State) {
        _state.update(block)
    }

    private fun fetchAllData(isRefreshing: Boolean = false) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, isRefreshing = isRefreshing) }

        val worksAsync = async { workRepository.fetchAllWorks() }

        val works = worksAsync.await()

        val calls = listOf(works)
        if (calls.any { it.isFailure }) {
            calls.first { it.isFailure }.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        _state.update { it.copy(isLoading = false, isRefreshing = false) }
    }
}
