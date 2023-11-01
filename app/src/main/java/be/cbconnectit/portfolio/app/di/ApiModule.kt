package be.cbconnectit.portfolio.app.di

import be.cbconnectit.portfolio.app.data.remote.api.ExperienceApi
import be.cbconnectit.portfolio.app.data.remote.api.ExperienceApiImpl
import be.cbconnectit.portfolio.app.data.remote.api.ServiceApi
import be.cbconnectit.portfolio.app.data.remote.api.ServiceApiImpl
import be.cbconnectit.portfolio.app.data.remote.api.TestimonialApi
import be.cbconnectit.portfolio.app.data.remote.api.TestimonialApiImpl
import be.cbconnectit.portfolio.app.data.remote.api.WorkApi
import be.cbconnectit.portfolio.app.data.remote.api.WorkApiImpl
import org.koin.dsl.module

val apiModule = module {
    single<ServiceApi> { ServiceApiImpl(get()) }
    single<WorkApi> { WorkApiImpl(get()) }
    single<TestimonialApi> { TestimonialApiImpl(get()) }
    single<ExperienceApi> { ExperienceApiImpl(get()) }
}
