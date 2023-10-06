package be.christiano.portfolio.app.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
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

                LandingScreenContent(state, viewModel::onEvent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreenContent(
    state: LandingState,
    onEvent: (LandingEvent) -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.height(82.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.landing_body)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        LayoutSystemCard(
                            modifier = Modifier
                                .weight(1f, false)
                                .widthIn(max = 300.dp)
                                .padding(
                                    start = if (index == 0) 0.dp else 8.dp,
                                    end = if (index == layoutSystems.size - 1) 0.dp else 8.dp,
                                ),
                            layoutSystem = layoutSystem,
                            checked = state.selectedLayoutSystem == layoutSystem,
                            onClick = {
                                onEvent(LandingEvent.ChangeSelectedLayoutSystem(layoutSystem))
                            })
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))

                //TODO: change the visibility animation!
                AnimatedVisibility(
                    modifier = Modifier.padding(horizontal = 42.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutSystemCard(
    modifier: Modifier = Modifier,
    layoutSystem: LayoutSystem,
    checked: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(if (checked) 30.dp else 0.dp))
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) innerColumn@{
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {

                //TODO: change the visibility animation!
                this@innerColumn.AnimatedVisibility(visible = checked) {
                    RadioButton(selected = checked, onClick = null)
                }

                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = layoutSystem.drawable),
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = layoutSystem.systemName),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
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

