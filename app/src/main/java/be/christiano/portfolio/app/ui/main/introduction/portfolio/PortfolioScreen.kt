package be.christiano.portfolio.app.ui.main.introduction.portfolio

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.domain.model.Link
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.extensions.startWeb
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.main.introduction.sections.components.WorkDetail
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

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

@OptIn(ExperimentalMaterial3Api::class)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(state.projects) { index, work ->
                    if (index != 0) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .fillMaxWidth(0.4f), thickness = 2.dp, color = MaterialTheme.colorScheme.primary
                        )
                    }

                    WorkDetail(
                        work = work,
                        imageStartAligned = index % 2 == 0,
                        onClick = {
                            onEvent(PortfolioEvent.OpenSocialLink(it))
                        }
                    )
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
            state = PortfolioState(projects = listOf(Work.previewData().copy(links = listOf(Link.previewData(), Link.previewData(), Link.previewData())))),
            onEvent = {}
        )
    }
}
