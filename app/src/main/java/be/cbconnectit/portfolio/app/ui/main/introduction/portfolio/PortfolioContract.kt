package be.cbconnectit.portfolio.app.ui.main.introduction.portfolio

import be.cbconnectit.portfolio.app.domain.model.Link
import be.cbconnectit.portfolio.app.domain.model.Work
import be.cbconnectit.portfolio.app.utils.MVI

interface PortfolioContract : MVI<PortfolioContract.State, PortfolioContract.Intent, PortfolioContract.Effect> {
    data class State(
        val isLoading: Boolean = false,
        val projects: List<Work> = emptyList(),
    )

    sealed class Intent {
        data class OpenSocialLink(val link: Link) : Intent()
    }

    sealed class Effect {
        data class OpenSocialLink(val link: Link) : Effect()
    }
}
