package be.christiano.portfolio.app.data.preferences

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(
    private val dataStorePreferences: DataStore<Preferences>
) : UserPreferences {
    private val TAG = this::class.java.simpleName

    override val displayMode: Flow<Int> = dataStorePreferences.data
        .catch { exception ->
            exception.localizedMessage?.let { Log.e(TAG, it) }
            emit(value = emptyPreferences())
        }
        .map { preferences ->
            preferences[PreferencesKeys.displayMode] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

    override suspend fun changeDisplayMode(displayMode: Int) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferencesKeys.displayMode] = displayMode
        }
    }

    private object PreferencesKeys {
        val displayMode = intPreferencesKey(name = "display_mode")
    }
}


interface UserPreferences {
    val displayMode: Flow<Int>

    suspend fun changeDisplayMode(displayMode: Int)
}