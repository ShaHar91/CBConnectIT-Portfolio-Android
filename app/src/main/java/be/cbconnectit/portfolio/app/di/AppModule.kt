package be.cbconnectit.portfolio.app.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import be.cbconnectit.portfolio.app.data.local.PortfolioDatabase
import be.cbconnectit.portfolio.app.data.preferences.UserPreferences
import be.cbconnectit.portfolio.app.data.preferences.UserPreferencesImpl
import be.cbconnectit.portfolio.app.data.remote.RestApiBuilder
import be.cbconnectit.portfolio.app.data.utils.TransactionProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
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

    single { TransactionProvider(get()) }

    single {
        Room
            .databaseBuilder(
                androidContext(),
                PortfolioDatabase::class.java,
                "portfolio.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}