package be.cbconnectit.portfolio.app.ui.main.introduction.experience

import be.cbconnectit.portfolio.app.domain.model.Experience
import be.cbconnectit.portfolio.app.utils.MVI

interface ExperienceContract: MVI<ExperienceContract.State, ExperienceContract.Intent, ExperienceContract.Effect> {
    data class State(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val experiences: List<Experience> = emptyList()
    )

    sealed class Intent {
        data object RefreshData : Intent()
    }

    sealed class Effect
}
