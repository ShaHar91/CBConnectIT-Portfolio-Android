package be.cbconnectit.portfolio.app.ui.main.settings

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import be.cbconnectit.portfolio.app.domain.enums.LayoutSystem
import be.cbconnectit.portfolio.app.utils.MVI

interface SettingsContract : MVI<SettingsContract.State, SettingsContract.Intent, SettingsContract.Effect> {
    data class State(
        val isLoading: Boolean = false,
        val selectedDisplayMode: Int = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
        val currentLayoutSystem: LayoutSystem? = null,
        val selectedLayoutSystem: LayoutSystem? = null,
        val selectedLayoutSystemExpanded: Boolean = false,
        val dynamicModeEnabled: Boolean = true,
        val language: String = "-",
        val appVersion: String = "-",
        val showConfirmationDialog: Boolean = false,
        val showUnsupportedDynamicFeatureDialog: Boolean = false
    ) {
        val hasDynamicSupport = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    sealed class Intent {
        data class ChangeDisplayMode(val displayMode: Int) : Intent()
        data class ChangeSelectedLayoutSystem(val layoutSystem: LayoutSystem) : Intent()
        data object PersistSelectedLayoutSystem : Intent()
        data object ResetSelectedLayoutSystem : Intent()
        data class ChangeDynamicMode(val dynamicModeEnabled: Boolean) : Intent()
        data class UpdateSelectedLayoutSystemExpanded(val expanded: Boolean) : Intent()
        data class ShowUnsupportedDynamicFeatureDialog(val shown: Boolean) : Intent()
    }

    sealed class Effect {
        data object RestartApplication : Effect()
    }
}
