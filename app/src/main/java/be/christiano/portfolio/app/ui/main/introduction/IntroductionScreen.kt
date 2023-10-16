package be.christiano.portfolio.app.ui.main.introduction

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.extensions.startIntentMail
import be.christiano.portfolio.app.extensions.startWeb
import be.christiano.portfolio.app.ui.main.introduction.sections.AboutMeSection
import be.christiano.portfolio.app.ui.main.introduction.sections.ExperienceSection
import be.christiano.portfolio.app.ui.main.introduction.sections.MainSection
import be.christiano.portfolio.app.ui.main.introduction.sections.PortfolioSection
import be.christiano.portfolio.app.ui.main.introduction.sections.ServiceSection
import be.christiano.portfolio.app.ui.main.introduction.sections.TestimonialsSection
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
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

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is IntroductionUiEvent.OpenSocialLink -> {
                    localContext.startWeb(
                        event.social.link,
                        toolbarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
                    )
                }

                is IntroductionUiEvent.OpenMailClient -> {
                    localContext.startIntentMail("bollachristiano@gmail.com", "Select an app") {
                        viewModel.showSnackbar("Something went wrong, please try again later")
                    }
                }
            }
        }
    }

    IntroductionScreenContent(
        state = state,
        navController = navController,
        { viewModel.CreateSnackBarHost() },
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroductionScreenContent(
    state: IntroductionState,
    navController: NavController,
    createSnackbarHost: @Composable () -> Unit = {},
    onEvent: (IntroductionEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(contentAlignment = Alignment.BottomCenter) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { createSnackbarHost() },
            floatingActionButton = {
                ExtendedFloatingActionButton(text = {
                    Text(text = stringResource(R.string.let_s_chat))
                }, icon = {
                    Icon(Icons.Default.MailOutline, "")
                }, onClick = {
                    onEvent(IntroductionEvent.OpenMailClient)
                })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                MainSection {
                    onEvent(IntroductionEvent.OpenSocialLink(it))
                }

                // This spacer is not the correct size because the MainImage has an offset which is being calculated in this
                Spacer(modifier = Modifier.height(40.dp))

                AboutMeSection(state.experienceInYears)

                Spacer(modifier = Modifier.height(62.dp))

                ServiceSection(state.services) {
                    onEvent(IntroductionEvent.OpenServiceList)
                }

                Spacer(modifier = Modifier.height(62.dp))

                PortfolioSection(state.projects) {
                    onEvent(IntroductionEvent.OpenPortfolioList)
                }

                Spacer(modifier = Modifier.height(62.dp))

                TestimonialsSection(state.testimonials) {
                    onEvent(IntroductionEvent.OpenTestimonialsList)
                }

                Spacer(modifier = Modifier.height(62.dp))

                ExperienceSection(state.experiences) {
                    onEvent(IntroductionEvent.OpenExperiencesList)
                }

                Spacer(modifier = Modifier.height(85.dp))
            }
        }

        AnimatedVisibility(visible = state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }

    //TODO: maybe add a SwipeToRefresh to the layout?
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IntroductionScreenPreview() {
    PortfolioTheme {
        IntroductionScreenContent(
            state = IntroductionState(),
            navController = rememberNavController(),
            onEvent = {})
    }
}
