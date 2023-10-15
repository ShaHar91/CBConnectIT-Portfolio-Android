package be.christiano.portfolio.app.data.preferences

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import be.christiano.portfolio.app.domain.enums.LayoutSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(
    private val dataStorePreferences: DataStore<Preferences>
) : UserPreferences {
    private val TAG = this::class.java.simpleName

    private val data = dataStorePreferences.data.catch { exception ->
        exception.localizedMessage?.let { Log.e(TAG, it) }
        emit(value = emptyPreferences())
    }

    override val userPrefs: Flow<UserPrefs> = data.map { preferences ->
        val pref = preferences[PreferencesKeys.dynamicModeEnabled]
        UserPrefs(
            preferences[PreferencesKeys.displayMode] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            preferences[PreferencesKeys.layoutSystem]?.let { LayoutSystem.values()[it] },
            pref == null || pref == true
        )
    }

    override suspend fun changeDisplayMode(displayMode: Int) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferencesKeys.displayMode] = displayMode
        }
    }

    override suspend fun changeLayoutSystem(layoutSystem: LayoutSystem) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferencesKeys.layoutSystem] = layoutSystem.ordinal
        }
    }

    override suspend fun changeDynamicEnabled(enabled: Boolean) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferencesKeys.dynamicModeEnabled] = enabled
        }
    }

    private object PreferencesKeys {
        val displayMode = intPreferencesKey(name = "display_mode")
        val layoutSystem = intPreferencesKey(name = "layout_system")
        val dynamicModeEnabled = booleanPreferencesKey(name = "dynamicMode")
    }
}

interface UserPreferences {
    val userPrefs: Flow<UserPrefs>

    suspend fun changeDisplayMode(displayMode: Int)
    suspend fun changeLayoutSystem(layoutSystem: LayoutSystem)
    suspend fun changeDynamicEnabled(enabled: Boolean)
}

data class UserPrefs(
    val displayMode: Int,
    val layoutSystem: LayoutSystem?,
    val dynamicEnabled: Boolean
)