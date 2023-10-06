package be.christiano.portfolio.app.di

import be.christiano.portfolio.app.ui.LandingViewModel
import be.christiano.portfolio.app.ui.main.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LandingViewModel)
    viewModelOf(::SettingsViewModel)
}