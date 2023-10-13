package be.christiano.portfolio.app.di

import be.christiano.portfolio.app.ui.landing.LandingViewModel
import be.christiano.portfolio.app.ui.main.MainViewModel
import be.christiano.portfolio.app.ui.main.components.ComponentsViewModel
import be.christiano.portfolio.app.ui.main.introduction.IntroductionViewModel
import be.christiano.portfolio.app.ui.main.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LandingViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::IntroductionViewModel)
    viewModelOf(::ComponentsViewModel)
    viewModelOf(::SettingsViewModel)
}