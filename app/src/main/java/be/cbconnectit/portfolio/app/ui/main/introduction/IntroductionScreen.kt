package be.cbconnectit.portfolio.app.ui.main.introduction

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.extensions.startIntentMail
import be.cbconnectit.portfolio.app.extensions.startWeb
import be.cbconnectit.portfolio.app.ui.main.destinations.ExperienceScreenDestination
import be.cbconnectit.portfolio.app.ui.main.destinations.PortfolioScreenDestination
import be.cbconnectit.portfolio.app.ui.main.destinations.ServiceDetailScreenDestination
import be.cbconnectit.portfolio.app.ui.main.destinations.ServicesScreenDestination
import be.cbconnectit.portfolio.app.ui.main.introduction.sections.ExperienceSection
import be.cbconnectit.portfolio.app.ui.main.introduction.sections.MainSection
import be.cbconnectit.portfolio.app.ui.main.introduction.sections.PortfolioSection
import be.cbconnectit.portfolio.app.ui.main.introduction.sections.ServiceSection
import be.cbconnectit.portfolio.app.ui.main.introduction.sections.TestimonialsSection
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun IntroductionScreen(
    navController: NavController,
    viewModel: IntroductionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val localContext = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { event ->
            when (event) {
                is IntroductionContract.Effect.OpenSocialLink -> {
                    localContext.startWeb(
                        event.link.url,
                        toolbarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
                    )
                }

                is IntroductionContract.Effect.OpenMailClient -> {
                    localContext.startIntentMail("bollachristiano@gmail.com", "Select an app") {
                        viewModel.showSnackbar("Something went wrong, please try again later")
                    }
                }

                IntroductionContract.Effect.OpenExperienceList -> navController.navigate(ExperienceScreenDestination)
                IntroductionContract.Effect.OpenPortfolio -> navController.navigate(PortfolioScreenDestination(arrayOf()))
                is IntroductionContract.Effect.OpenServiceDetail -> navController.navigate(ServiceDetailScreenDestination(serviceId = event.serviceId))
                IntroductionContract.Effect.OpenServiceList -> navController.navigate(ServicesScreenDestination)
                else -> Unit
            }
        }
    }

    IntroductionScreenContent(
        state = state,
        { viewModel.CreateSnackBarHost() },
        sendIntent = viewModel::sendIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroductionScreenContent(
    state: IntroductionContract.State,
    createSnackBarHost: @Composable () -> Unit = {},
    sendIntent: (IntroductionContract.Intent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(contentAlignment = Alignment.BottomCenter) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { createSnackBarHost() },
            floatingActionButton = {
                ExtendedFloatingActionButton(text = {
                    Text(text = stringResource(R.string.let_s_chat))
                }, icon = {
                    Icon(painterResource(id = R.drawable.ic_mail), "")
                }, onClick = {
                    sendIntent(IntroductionContract.Intent.OpenMailClient)
                })
            }
        ) { paddingValues ->
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { sendIntent(IntroductionContract.Intent.RefreshData) },
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    MainSection {
                        sendIntent(IntroductionContract.Intent.OpenSocialLink(it))
                    }

//                    // This spacer is not the correct size because the MainImage has an offset which is being calculated in this
//                    Spacer(modifier = Modifier.height(40.dp))
//
//                    AboutMeSection(state.experienceInYears)

                    Spacer(modifier = Modifier.height(62.dp))

                    ServiceSection(
                        state.services,
                        headerActionClicked = {
                            sendIntent(IntroductionContract.Intent.OpenServiceList)
                        },
                        serviceActionClicked = { sendIntent(IntroductionContract.Intent.OpenServiceDetail(it)) }
                    )

                    Spacer(modifier = Modifier.height(62.dp))

                    PortfolioSection(
                        projects = state.projects,
                        selectedWork = state.selectedProject,
                        onWorkClicked = {
                            sendIntent(IntroductionContract.Intent.UpdateSelectedWork(it))
                        },
                        actionClicked = {
                            sendIntent(IntroductionContract.Intent.OpenPortfolioList)
                        }
                    )

                    Spacer(modifier = Modifier.height(62.dp))

                    TestimonialsSection(state.testimonials) {
                        sendIntent(IntroductionContract.Intent.OpenTestimonialsList)
                    }

                    Spacer(modifier = Modifier.height(62.dp))

                    ExperienceSection(state.experiences) {
                        sendIntent(IntroductionContract.Intent.OpenExperiencesList)
                    }

                    Spacer(modifier = Modifier.height(100.dp))
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
fun IntroductionScreenPreview() {
    PortfolioTheme {
        IntroductionScreenContent(
            state = IntroductionContract.State(),
            sendIntent = {}
        )
    }
}
