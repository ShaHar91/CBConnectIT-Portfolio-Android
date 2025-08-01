package be.cbconnectit.portfolio.app.ui.main.introduction.services

import androidx.lifecycle.viewModelScope
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
import be.cbconnectit.portfolio.app.ui.base.BaseComposeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val serviceRepository: ServiceRepository
) : BaseComposeViewModel(), ServicesContract {

    private val _state = MutableStateFlow(ServicesContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ServicesContract.Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        fetchServicesData()

        serviceRepository.findAllServices().onEach { services ->
            updateState { it.copy(services = services) }
        }.launchIn(viewModelScope)
    }

    private fun fetchServicesData(isRefreshing: Boolean = false) = viewModelScope.launch {
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

    override fun sendIntent(intent: ServicesContract.Intent) = viewModelScope.launch {
        when (intent) {
            is ServicesContract.Intent.OpenServiceDetail -> emitEffect(ServicesContract.Effect.OpenServiceDetail(intent.serviceId))
            is ServicesContract.Intent.RefreshData -> fetchServicesData(true)
        }
    }

    override fun emitEffect(effect: ServicesContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (ServicesContract.State) -> ServicesContract.State) {
        _state.update(block)
    }
}
