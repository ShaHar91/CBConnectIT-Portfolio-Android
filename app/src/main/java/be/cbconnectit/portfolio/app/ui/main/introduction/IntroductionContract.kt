package be.cbconnectit.portfolio.app.ui.main.introduction

import be.cbconnectit.portfolio.app.domain.model.Experience
import be.cbconnectit.portfolio.app.domain.model.Link
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.model.Testimonial
import be.cbconnectit.portfolio.app.domain.model.Work
import be.cbconnectit.portfolio.app.utils.MVI

interface IntroductionContract : MVI<IntroductionContract.State, IntroductionContract.Intent, IntroductionContract.Effect> {
    data class State(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val socialLinks: List<Link> = emptyList(),
        val experienceInYears: Int = 0,
        val services: List<Service> = emptyList(),
        val projects: List<Work> = emptyList(),
        val testimonials: List<Testimonial> = emptyList(),
        val experiences: List<Experience> = emptyList(),
        val selectedProject: Work? = null
    )

    sealed class Intent {
        data class OpenSocialLink(val link: Link) : Intent()
        data object OpenMailClient : Intent()
        data object OpenServiceList : Intent()
        data class OpenServiceDetail(val serviceId: String) : Intent()
        data object OpenPortfolioList : Intent()
        data class UpdateSelectedWork(val work: Work) : Intent()
        data object OpenTestimonialsList : Intent()
        data object OpenExperiencesList : Intent()
        data object RefreshData : Intent()
    }

    sealed class Effect {
        data class OpenSocialLink(val link: Link) : Effect()
        data object OpenMailClient : Effect()
        data object OpenExperienceList : Effect()
        data object OpenPortfolio : Effect()
        data object OpenServiceList : Effect()
        data class OpenServiceDetail(val serviceId: String) : Effect()
    }
}