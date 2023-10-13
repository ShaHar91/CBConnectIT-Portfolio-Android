package be.christiano.portfolio.app.ui.main.settings

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.extensions.findActivity
import be.christiano.portfolio.app.ui.components.DataRow
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.components.ToggleableDataRow
import be.christiano.portfolio.app.ui.components.ValueDataRow
import be.christiano.portfolio.app.ui.landing.LayoutSystem
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
    if (state.showConfirmationDialog) {
        AlertDialog(
            title = {
                Text(text ="Are your sure?")
            },
            text = {
                Text(text = "Changing the layout system will result in a full reboot of the app. Are you sure you want to continue?")
            },
            onDismissRequest = {
                onEvent(SettingsEvent.ResetSelectedLayoutSystem)
            },
            confirmButton = {
                TextButton(onClick = { onEvent(SettingsEvent.PersistSelectedLayoutSystem) }) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(SettingsEvent.ResetSelectedLayoutSystem) }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { DefaultAppBar(navController = navController, appBarTitle = "Settings") }
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            DataRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                labelText = "Mode",
                endSlot = {

//                    onEvent(SettingsEvent.ChangeDisplayMode(AppCompatDelegate.MODE_NIGHT_YES))
                }
            )

            Divider(modifier = Modifier.padding(start = 16.dp))

            DataRow(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                labelText = "Layout system",
                endSlot = {
                    Box {

                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ),
                            onClick = {
                                onEvent(SettingsEvent.UpdateSelectedLayoutSystemExpanded(!state.selectedLayoutSystemExpanded))
                            }) {
                            Text(
                                text = state.currentLayoutSystem?.systemName?.let { stringResource(id = it) } ?: ""
                            )

                            val icon = if (state.selectedLayoutSystemExpanded) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            }
                            Icon(icon, contentDescription = "")
                        }

                        DropdownMenu(
                            expanded = state.selectedLayoutSystemExpanded,
                            onDismissRequest = {
                                onEvent(SettingsEvent.UpdateSelectedLayoutSystemExpanded(false))
                            }) {
                            LayoutSystem.values().forEach {

                                DropdownMenuItem(
                                    trailingIcon = {
                                        if (state.selectedLayoutSystem == it) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            style = MaterialTheme.typography.bodyLarge,
                                            text = stringResource(id = it.systemName)
                                        )
                                    },
                                    onClick = {
                                        onEvent(SettingsEvent.ChangeSelectedLayoutSystem(it))
                                    })
                            }
                        }
                    }
                }
            )

            Divider(modifier = Modifier.padding(start = 16.dp))

            ToggleableDataRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                labelText = "Dynamic (Material You)",
                checked = state.dynamicModeEnabled,
                onCheckedChange = {
                    onEvent(SettingsEvent.ChangeDynamicMode(it))
                })

            Divider(modifier = Modifier.padding(start = 16.dp))

            ValueDataRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                labelText = "App version",
                valueText = state.appVersion,
                endIcon = null
            )

        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    PortfolioTheme {
        SettingsScreenContent(
            state = SettingsState(currentLayoutSystem = LayoutSystem.Compose),
            navController = rememberNavController(),
            onEvent = {})
    }
}