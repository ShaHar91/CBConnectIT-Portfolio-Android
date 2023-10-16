package be.christiano.portfolio.app.di

import be.christiano.portfolio.app.data.remote.api.ExperienceApi
import be.christiano.portfolio.app.data.remote.api.ExperienceApiImpl
import be.christiano.portfolio.app.data.remote.api.ServiceApi
import be.christiano.portfolio.app.data.remote.api.ServiceApiImpl
import be.christiano.portfolio.app.data.remote.api.TestimonialApi
import be.christiano.portfolio.app.data.remote.api.TestimonialApiImpl
import be.christiano.portfolio.app.data.remote.api.WorkApi
import be.christiano.portfolio.app.data.remote.api.WorkApiImpl
import org.koin.dsl.module

val apiModule = module {
    single<ServiceApi> { ServiceApiImpl(get()) }
    single<WorkApi> { WorkApiImpl(get()) }
    single<TestimonialApi> { TestimonialApiImpl(get()) }
    single<ExperienceApi> { ExperienceApiImpl(get()) }
}
