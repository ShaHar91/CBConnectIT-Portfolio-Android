package be.cbconnectit.portfolio.app.ui.main.introduction.services.serviceDetail

import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.utils.MVI

interface ServiceDetailContract : MVI<ServiceDetailContract.State, ServiceDetailContract.Intent, ServiceDetailContract.Effect> {
    sealed class Intent {
        data class OpenProjectByTag(val tagId: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val services: List<Service> = emptyList(),
        val parentService: Service? = null
    )

    sealed class Effect {
        data class OpenProjectByTag(val tagId: String) : Effect()
    }
}
