package be.christiano.portfolio.app.di

import be.christiano.portfolio.app.data.repository.ExperienceRepositoryImpl
import be.christiano.portfolio.app.data.repository.ServiceRepositoryImpl
import be.christiano.portfolio.app.data.repository.TestimonialRepositoryImpl
import be.christiano.portfolio.app.data.repository.WorkRepositoryImpl
import be.christiano.portfolio.app.domain.repository.ExperienceRepository
import be.christiano.portfolio.app.domain.repository.ServiceRepository
import be.christiano.portfolio.app.domain.repository.TestimonialRepository
import be.christiano.portfolio.app.domain.repository.WorkRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ServiceRepository> { ServiceRepositoryImpl(get(), get()) }
    single<WorkRepository> { WorkRepositoryImpl(get(), get(), get(), get(), get(),get()) }
    single<TestimonialRepository> { TestimonialRepositoryImpl(get(), get()) }
    single<ExperienceRepository> { ExperienceRepositoryImpl(get(), get()) }
}