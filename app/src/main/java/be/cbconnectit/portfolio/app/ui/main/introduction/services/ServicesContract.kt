package be.cbconnectit.portfolio.app.ui.main.introduction.services

import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.utils.MVI

interface ServicesContract : MVI<ServicesContract.State, ServicesContract.Intent, ServicesContract.Effect> {

    sealed class Intent {
        data class OpenServiceDetail(val serviceId: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val services: List<Service> = emptyList()
    )

    sealed class Effect {
        data class OpenServiceDetail(val serviceId: String) : Effect()
    }
}
