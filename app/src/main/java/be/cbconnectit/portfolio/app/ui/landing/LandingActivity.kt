package be.cbconnectit.portfolio.app.ui.landing

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.domain.enums.LayoutSystem
import be.cbconnectit.portfolio.app.ui.components.ChoiceCard
import be.cbconnectit.portfolio.app.ui.main.MainActivity
import be.cbconnectit.portfolio.app.ui.main.MainComposeActivity
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModel<LandingViewModel>()

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isGettingData.value
        }

        super.onCreate(savedInstanceState)

        setContent {
            PortfolioTheme {
                val state by viewModel.state.collectAsState()

                if (state.currentLayoutSystem != null) {
                    navigateToNext(state.currentLayoutSystem, this@LandingActivity)
                    return@PortfolioTheme
                }

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            LandingUiEvent.NavigateToNextScreen -> navigateToNext(state.selectedLayoutSystem, this@LandingActivity)
                        }
                    }
                }

                LandingScreenContent(state, viewModel::onEvent)
            }
        }
    }
}

private fun navigateToNext(layoutSystem: LayoutSystem?, context: Context) {
    val intent = when (layoutSystem) {
        LayoutSystem.Xml -> MainActivity.newIntent(context)
        LayoutSystem.Compose -> MainComposeActivity.newIntent(context)
        else -> return
    }

    context.startActivity(intent)
}

@Composable
fun LandingScreenContent(
    state: LandingState,
    onEvent: (LandingEvent) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(82.dp))

                Text(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.landing_body)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.landing_subtitle)
                )

                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    val layoutSystems = LayoutSystem.values()

                    layoutSystems.forEachIndexed { index, layoutSystem ->
                        ChoiceCard(
                            modifier = Modifier
                                .weight(1f, false)
                                .widthIn(max = 250.dp)
                                .padding(
                                    start = if (index == 0) 0.dp else 8.dp,
                                    end = if (index == layoutSystems.size - 1) 0.dp else 8.dp,
                                ),
                            title = layoutSystem.systemName,
                            drawable = layoutSystem.drawable,
                            checked = state.selectedLayoutSystem == layoutSystem,
                            onClick = {
                                onEvent(LandingEvent.ChangeSelectedLayoutSystem(layoutSystem))
                            })
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))

                AnimatedVisibility(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 42.dp),
                    visible = state.selectedLayoutSystem != null
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        text = state.selectedLayoutSystem?.description?.let { stringResource(it) } ?: ""
                    )
                }
            }

            Button(
                modifier = Modifier.padding(bottom = 28.dp),
                enabled = state.selectedLayoutSystem != null,
                onClick = {
                    onEvent(LandingEvent.UpdateLayoutSystem)
                }
            ) {
                Text(text = stringResource(R.string.common_continue))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LandingScreenPreview() {
    PortfolioTheme {
        LandingScreenContent(LandingState(selectedLayoutSystem = LayoutSystem.Xml))
    }
}

