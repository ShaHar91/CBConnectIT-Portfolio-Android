package be.christiano.portfolio.app.ui.main.settings

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.extensions.findActivity
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.landing.LayoutSystem
import be.christiano.portfolio.app.ui.main.introduction.IntroductionScreenContent
import be.christiano.portfolio.app.ui.main.introduction.IntroductionState
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val activity = LocalContext.current.findActivity()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SettingsUiEvent.RestartApplication -> {
                    val i = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
                    startActivity(activity, Intent.makeRestartActivityTask(i?.component), null)
                    Runtime.getRuntime().exit(0)
                }
            }
        }
    }

    SettingsScreenContent(
        state = state,
        navController = navController,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    state: SettingsState,
    navController: NavController,
    onEvent: (SettingsEvent) -> Unit
) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { DefaultAppBar(navController = navController, appBarTitle = "Settings") }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            Button(onClick = {
                onEvent(SettingsEvent.ChangeSelectedLayoutSystem(LayoutSystem.Xml))
            }) {
                Text(text = "Change layout system")
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    PortfolioTheme {
        SettingsScreenContent(state = SettingsState(), navController = rememberNavController(), onEvent = {})
    }
}