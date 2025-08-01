package be.cbconnectit.portfolio.app.ui.main.introduction.services.serviceDetail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.model.previewData
import be.cbconnectit.portfolio.app.ui.main.destinations.PortfolioScreenDestination
import be.cbconnectit.portfolio.app.ui.main.introduction.services.components.CollapsingServiceToolbar
import be.cbconnectit.portfolio.app.ui.main.introduction.services.components.ServiceItem
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.flow.collectLatest
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.SnapConfig
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun ServiceDetailScreen(
    navController: NavController,
    serviceId: String,
    viewModel: ServiceDetailViewModel = koinViewModel {
        parametersOf(serviceId)
    }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { event ->
            when (event) {
                is ServiceDetailContract.Effect.OpenProjectByTag -> navController.navigate(PortfolioScreenDestination(arrayOf(event.tagId)))
            }
        }
    }
    ServiceDetailScreenContent(
        state = state,
        navController = navController,
        sendIntent = viewModel::sendIntent
    ) { viewModel.CreateSnackBarHost() }
}

@OptIn(ExperimentalToolbarApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreenContent(
    state: ServiceDetailContract.State,
    navController: NavController,
    sendIntent: (ServiceDetailContract.Intent) -> Unit,
    createSnackBarHost: @Composable () -> Unit = {},
) {
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    var hasEffectRun by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = state.parentService?.bannerDescription != null) {
        if (hasEffectRun) return@LaunchedEffect

        if (state.parentService?.bannerDescription != null) {
            toolbarState.expand()
            hasEffectRun = true
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { sendIntent(ServiceDetailContract.Intent.RefreshData) },
        modifier = Modifier.fillMaxSize()
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier,
            state = toolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            snapConfig = SnapConfig(),
            toolbarScrollable = true,
            toolbar = {
                CollapsingServiceToolbar(
                    toolbarState = toolbarState,
                    navController = navController,
                    title = state.parentService?.title ?: "",
                    body = state.parentService?.bannerDescription ?: "",
                    imageUrl = state.parentService?.bannerImageUrl
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                state.services.forEachIndexed { index, service ->
                    ServiceItem(service = service, shouldColorBackground = index % 2 == 1) {
                        service.tag?.let {
                            sendIntent(ServiceDetailContract.Intent.OpenProjectByTag(it.id))
                        }
                    }
                }

                val extraInfo = state.parentService?.extraInfo
                if (!extraInfo.isNullOrEmpty()) {

                    Spacer(modifier = Modifier.height(48.dp))

                    MarkdownText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        markdown = extraInfo,
                        style = MaterialTheme.typography.bodyLarge,
                        linkColor = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(visible = state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        createSnackBarHost()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServiceDetailScreenContentPreview() {
    PortfolioTheme {
        ServiceDetailScreenContent(
            navController = rememberNavController(),
            state = ServiceDetailContract.State(services = listOf(Service.previewData())),
            sendIntent = {}
        )
    }
}
