package be.cbconnectit.portfolio.app.ui.main.introduction.services.serviceDetail

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceDetailViewModel(
    private val serviceRepository: ServiceRepository,
    serviceId: String
) : BaseComposeViewModel(), ServiceDetailContract {

    private val _state = MutableStateFlow(ServiceDetailContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ServiceDetailContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        fetchServiceDetailData()

        combine(
            serviceRepository.findParentServiceName(serviceId),
            serviceRepository.findAllServices(serviceId)
        ) { parentService, services ->
            val currentState = _state.value
            currentState.copy(
                parentService = parentService,
                services = services,
            )
        }.onEach { newState ->
            updateState { newState }
        }.launchIn(viewModelScope)
    }

    override fun sendIntent(intent: ServiceDetailContract.Intent) = viewModelScope.launch {
        when (intent) {
            is ServiceDetailContract.Intent.OpenProjectByTag -> emitEffect(ServiceDetailContract.Effect.OpenProjectByTag(intent.tagId))
            is ServiceDetailContract.Intent.RefreshData -> fetchServiceDetailData(true)
        }
    }

    override fun emitEffect(effect: ServiceDetailContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (ServiceDetailContract.State) -> ServiceDetailContract.State) {
        _state.update(block)
    }

    private fun fetchServiceDetailData(isRefreshing: Boolean = false) = viewModelScope.launch {
        updateState { it.copy(isLoading = true, isRefreshing = isRefreshing) }

        val call = serviceRepository.fetchAllServices()
        if (call.isFailure) {
            call.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        updateState { it.copy(isLoading = false, isRefreshing = false) }
    }
}
