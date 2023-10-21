package be.christiano.portfolio.app.ui.main.introduction.portfolio

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.extensions.startWeb
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.components.textflow.TextFlow
import be.christiano.portfolio.app.ui.components.textflow.TextFlowObstacleAlignment
import be.christiano.portfolio.app.ui.main.introduction.sections.components.LinkBar
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

//TODO wrap text around image
// compose: https://stackoverflow.com/questions/70514385/how-do-i-wrap-text-around-an-image-or-a-composable
//  https://github.com/oleksandrbalan/textflow

// xml: https://stackoverflow.com/questions/2248759/how-to-layout-text-to-flow-around-an-image

@Destination
@Composable
fun PortfolioScreen(
    navController: NavController,
    viewModel: PortfolioViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val localContext = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PortfolioUiEvent.OpenSocialLink -> {
                    localContext.startWeb(
                        event.link.url,
                        toolbarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
                    )
                }
            }
        }
    }

    PortfolioScreenContent(
        state = state,
        navController = navController,
        { viewModel.CreateSnackBarHost() },
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PortfolioScreenContent(
    state: PortfolioState,
    navController: NavController,
    createSnackBarHost: @Composable () -> Unit = {},
    onEvent: (PortfolioEvent) -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { DefaultAppBar(navController = navController, appBarTitle = stringResource(id = R.string.portfolio)) },
            snackbarHost = { createSnackBarHost() }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(state.projects) { index, work ->
                    Column {

                        if (index != 0) {
                            Spacer(modifier = Modifier.height(48.dp))
                        }

                        Text(text = work.title, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

                        Spacer(modifier = Modifier.height(12.dp))

                        TextFlow(
                            obstacleAlignment = if (index % 2 == 0) TextFlowObstacleAlignment.TopStart else TextFlowObstacleAlignment.TopEnd,
                            text = work.description,
                            style = MaterialTheme.typography.bodyLarge
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    bottom = 4.dp,
                                    start = if (index % 2 == 0) 0.dp else 16.dp,
                                    end = if (index % 2 == 0) 16.dp else 0.dp
                                )
                            ) {
                                AsyncImage(
                                    modifier = Modifier.size(width = 88.dp, height = 148.dp),
                                    model = work.image,
                                    contentDescription = ""
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                LinkBar(links = work.links) {
                                    onEvent(PortfolioEvent.OpenSocialLink(it))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
                        ) {
                            work.tags.forEach {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 4.dp),
                                    text = it.name,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExperienceScreenContentPreview() {
    PortfolioTheme {
        PortfolioScreenContent(
            navController = rememberNavController(),
            state = PortfolioState(projects = listOf(Work.previewData())),
            onEvent = {}
        )
    }
}
