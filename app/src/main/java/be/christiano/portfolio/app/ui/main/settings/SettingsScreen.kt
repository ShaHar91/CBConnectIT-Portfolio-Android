package be.christiano.portfolio.app.ui.main.settings

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.domain.enums.DisplayMode
import be.christiano.portfolio.app.domain.enums.LayoutSystem
import be.christiano.portfolio.app.extensions.findActivity
import be.christiano.portfolio.app.ui.components.ConfirmationDialog
import be.christiano.portfolio.app.ui.components.DataRow
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.components.InformativeDialog
import be.christiano.portfolio.app.ui.components.ToggleableDataRow
import be.christiano.portfolio.app.ui.components.ValueDataRow
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
        ConfirmationDialog(
            title = stringResource(R.string.change_layout_mode_title),
            text = stringResource(R.string.change_layout_mode_body),
            confirmButtonText = stringResource(R.string.common_continue),
            dismissButtonText = stringResource(R.string.cancel),
            onDismissRequest = { onEvent(SettingsEvent.ResetSelectedLayoutSystem) },
            onConfirmButtonClicked = { onEvent(SettingsEvent.PersistSelectedLayoutSystem) }
        )
    }

    if (state.showUnsupportedDynamicFeatureDialog) {
        InformativeDialog(
            title = stringResource(R.string.unsupported_feature),
            text = stringResource(R.string.unsupported_feature_dynamic_colors_dialog_body),
            confirmButtonText = stringResource(R.string.ok),
            onDismissRequest = { onEvent(SettingsEvent.ShowUnsupportedDynamicFeatureDialog(false)) }
        )
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { DefaultAppBar(navController = navController, appBarTitle = stringResource(id = R.string.settings)) }
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            DataRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                labelText = stringResource(R.string.display_mode),
                endSlot = {
                    SingleChoiceSegmentedButtonRow {
                        val displayModes = DisplayMode.values()

                        displayModes.forEachIndexed { index, displayMode ->
                            SegmentedButton(
                                modifier = Modifier.size(width = 50.dp, height = 32.dp),
                                selected = displayMode.options.contains(state.selectedDisplayMode),
                                onClick = {
                                    onEvent(SettingsEvent.ChangeDisplayMode(displayMode.options.first()))
                                },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = displayModes.count()
                                ),
                                icon = {}
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = displayMode.icon),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            )

            HorizontalDivider(modifier = Modifier.padding(start = 16.dp))

            DataRow(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                labelText = stringResource(R.string.layout_system),
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
                                text = state.currentLayoutSystem?.systemName?.let {
                                    stringResource(
                                        id = it
                                    )
                                } ?: ""
                            )

                            val icon = if (state.selectedLayoutSystemExpanded) {
                                painterResource(id = R.drawable.ic_drop_up)
                            } else {
                                painterResource(id = R.drawable.ic_drop_down)
                            }
                            Icon(icon, contentDescription = "")
                        }

                        DropdownMenu(
                            expanded = state.selectedLayoutSystemExpanded,
                            onDismissRequest = {
                                onEvent(SettingsEvent.UpdateSelectedLayoutSystemExpanded(false))
                            }
                        ) {
                            LayoutSystem.values().forEachIndexed { index, layoutSystem ->
                                if (index != 0) {
                                    HorizontalDivider()
                                }

                                DropdownMenuItem(
                                    trailingIcon = {
                                        if (state.selectedLayoutSystem == layoutSystem) {
                                            Icon(
                                                painterResource(id = R.drawable.ic_check),
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            style = MaterialTheme.typography.bodyLarge,
                                            text = stringResource(id = layoutSystem.systemName)
                                        )
                                    },
                                    onClick = {
                                        onEvent(SettingsEvent.ChangeSelectedLayoutSystem(layoutSystem))
                                    }
                                )
                            }
                        }
                    }
                }
            )

            HorizontalDivider(modifier = Modifier.padding(start = 16.dp))

            Row {
                if (!state.hasDynamicSupport) {
                    IconButton(onClick = {
                        onEvent(SettingsEvent.ShowUnsupportedDynamicFeatureDialog(true))
                    }) {
                        Icon(painterResource(id = R.drawable.ic_info), null)
                    }
                }

                ToggleableDataRow(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    labelText = stringResource(R.string.dynamic_material_you),
                    checked = state.dynamicModeEnabled,
                    enabled = state.hasDynamicSupport,
                    onCheckedChange = {
                        onEvent(SettingsEvent.ChangeDynamicMode(it))
                    })
            }

            HorizontalDivider(modifier = Modifier.padding(start = 16.dp))

            ValueDataRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                labelText = stringResource(R.string.app_version),
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