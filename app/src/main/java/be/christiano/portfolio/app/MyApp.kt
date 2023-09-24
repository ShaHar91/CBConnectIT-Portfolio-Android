package be.christiano.portfolio.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import be.christiano.portfolio.app.data.preferences.UserPreferences
import be.christiano.portfolio.app.di.KoinInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        KoinInitializer.init(instance)

        CoroutineScope(Dispatchers.IO).launch {
            val mode = get<UserPreferences>().displayMode.first()
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}