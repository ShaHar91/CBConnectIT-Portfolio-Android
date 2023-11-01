package be.cbconnectit.portfolio.app.di

import be.cbconnectit.portfolio.app.data.local.PortfolioDatabase
import org.koin.dsl.module

val daoModule = module {
    single { get<PortfolioDatabase>().experienceDao }
    single { get<PortfolioDatabase>().serviceDao }
    single { get<PortfolioDatabase>().testimonialDao }
    single { get<PortfolioDatabase>().workDao }
    single { get<PortfolioDatabase>().tagDao }
    single { get<PortfolioDatabase>().workTagCrossRefDao }
    single { get<PortfolioDatabase>().linkDao }
}