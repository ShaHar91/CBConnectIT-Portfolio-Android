package be.christiano.portfolio.app.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import be.christiano.portfolio.app.data.preferences.UserPreferences
import be.christiano.portfolio.app.data.preferences.UserPreferencesImpl
import be.christiano.portfolio.app.data.remote.RestApiBuilder
import be.christiano.portfolio.app.data.remote.api.ExperienceApi
import be.christiano.portfolio.app.data.remote.api.ExperienceApiImpl
import be.christiano.portfolio.app.data.remote.api.ServiceApi
import be.christiano.portfolio.app.data.remote.api.ServiceApiImpl
import be.christiano.portfolio.app.data.remote.api.TestimonialApi
import be.christiano.portfolio.app.data.remote.api.TestimonialApiImpl
import be.christiano.portfolio.app.data.remote.api.WorkApi
import be.christiano.portfolio.app.data.remote.api.WorkApiImpl
import be.christiano.portfolio.app.data.repository.ExperienceRepositoryImpl
import be.christiano.portfolio.app.data.repository.ServiceRepositoryImpl
import be.christiano.portfolio.app.data.repository.TestimonialRepositoryImpl
import be.christiano.portfolio.app.data.repository.WorkRepositoryImpl
import be.christiano.portfolio.app.domain.repository.ExperienceRepository
import be.christiano.portfolio.app.domain.repository.ServiceRepository
import be.christiano.portfolio.app.domain.repository.TestimonialRepository
import be.christiano.portfolio.app.domain.repository.WorkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        // Creates "DataStore<Preferences>"
        PreferenceDataStoreFactory.create(scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                get<Context>().preferencesDataStoreFile("user_preference")
            })
    }

    single<UserPreferences> {
        UserPreferencesImpl(get())
    }

    singleOf(::RestApiBuilder)

    single<ServiceApi> { ServiceApiImpl(get()) }
    single<WorkApi> { WorkApiImpl(get()) }
    single<TestimonialApi> { TestimonialApiImpl(get()) }
    single<ExperienceApi> { ExperienceApiImpl(get()) }

    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    single<WorkRepository> { WorkRepositoryImpl(get()) }
    single<TestimonialRepository> { TestimonialRepositoryImpl(get()) }
    single<ExperienceRepository> { ExperienceRepositoryImpl(get()) }
}
