package be.christiano.portfolio.app.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import be.christiano.portfolio.app.data.preferences.UserPreferences
import be.christiano.portfolio.app.data.preferences.UserPreferencesImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {
    single {
        PreferenceDataStoreFactory.create(scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                get<Context>().preferencesDataStoreFile("user_preference")
            })
    }

    single<UserPreferences> {
        UserPreferencesImpl(get())
    }
}
