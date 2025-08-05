package be.cbconnectit.portfolio.app.ui.main.introduction.services

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.model.previewData
import be.cbconnectit.portfolio.app.ui.main.destinations.ServiceDetailScreenDestination
import be.cbconnectit.portfolio.app.ui.main.introduction.services.components.CollapsingServiceToolbar
import be.cbconnectit.portfolio.app.ui.main.introduction.services.components.ServiceItem
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.flow.collectLatest
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.SnapConfig
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ServicesScreen(
    navController: NavController,
    viewModel: ServicesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { event ->
            when (event) {
                is ServicesContract.Effect.OpenServiceDetail -> navController.navigate(ServiceDetailScreenDestination(event.serviceId))
            }
        }
    }

    ServicesScreenContent(
        state = state,
        navController = navController,
        sendIntent = viewModel::sendIntent,
    ) { viewModel.CreateSnackBarHost() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreenContent(
    state: ServicesContract.State,
    navController: NavController,
    sendIntent: (ServicesContract.Intent) -> Unit,
    createSnackBarHost: @Composable () -> Unit = {},
) {
    val toolbarState = rememberCollapsingToolbarScaffoldState()

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { sendIntent(ServicesContract.Intent.RefreshData) },
        modifier = Modifier
            .fillMaxSize()
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            state = toolbarState,
            snapConfig = SnapConfig(),
            toolbarScrollable = true,
            toolbar = {
                CollapsingServiceToolbar(
                    toolbarState = toolbarState,
                    navController = navController,
                    title = stringResource(id = R.string.my_services),
                    body = stringResource(id = R.string.my_services_description)
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
                        sendIntent(ServicesContract.Intent.OpenServiceDetail(service.id))
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(visible = state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        createSnackBarHost()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServicesScreenContentPreview() {
    PortfolioTheme {
        ServicesScreenContent(
            navController = rememberNavController(),
            state = ServicesContract.State(services = listOf(Service.previewData())),
            sendIntent = {}
        )
    }
}
