package be.cbconnectit.portfolio.app.di

import be.cbconnectit.portfolio.app.ui.landing.LandingViewModel
import be.cbconnectit.portfolio.app.ui.main.MainViewModel
import be.cbconnectit.portfolio.app.ui.main.components.ComponentsViewModel
import be.cbconnectit.portfolio.app.ui.main.introduction.IntroductionViewModel
import be.cbconnectit.portfolio.app.ui.main.introduction.experience.ExperienceViewModel
import be.cbconnectit.portfolio.app.ui.main.introduction.portfolio.PortfolioViewModel
import be.cbconnectit.portfolio.app.ui.main.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LandingViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::IntroductionViewModel)
    viewModelOf(::PortfolioViewModel)
    viewModelOf(::ExperienceViewModel)
    viewModelOf(::ComponentsViewModel)
    viewModelOf(::SettingsViewModel)
}