package be.cbconnectit.portfolio.app.ui.main.introduction.services

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val serviceRepository: ServiceRepository
) : BaseComposeViewModel() {

    private val _state = MutableStateFlow(ServicesState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<ServicesUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        fetchServicesData()

        serviceRepository.findAllServices().onEach { services ->
            _state.update { it.copy(services = services) }
        }.launchIn(viewModelScope)
    }

    private fun fetchServicesData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val call = serviceRepository.fetchAllServices()
        if (call.isFailure) {
            call.exceptionOrNull()?.let {
                it.printStackTrace()
                showSnackbar(it.message)
            }
        }

        _state.update { it.copy(isLoading = false) }
    }

    fun onEvent(event: ServicesEvent) = viewModelScope.launch {
        when (event) {
            is ServicesEvent.OpenServiceDetail -> _eventFlow.send(ServicesUiEvent.OpenServiceDetail(event.serviceId))
        }
    }
}

sealed class ServicesEvent {
    data class OpenServiceDetail(val serviceId: String) : ServicesEvent()
}

data class ServicesState(
    val isLoading: Boolean = false,
    val services: List<Service> = emptyList()
)

sealed class ServicesUiEvent {
    data class OpenServiceDetail(val serviceId: String) : ServicesUiEvent()
}