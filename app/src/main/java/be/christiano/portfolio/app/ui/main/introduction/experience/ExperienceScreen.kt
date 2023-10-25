package be.christiano.portfolio.app.ui.main.introduction.experience

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import be.christiano.portfolio.app.ui.main.introduction.sections.components.ExperienceItem
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ExperienceScreen(
    navController: NavController,
    viewModel: ExperienceViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    ExperienceScreenContent(
        state = state,
        navController = navController,
    ) { viewModel.CreateSnackBarHost() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceScreenContent(
    state: ExperienceState,
    navController: NavController,
    createSnackBarHost: @Composable () -> Unit = {},
) {

    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { DefaultAppBar(navController = navController, appBarTitle = stringResource(id = R.string.experiences)) },
            snackbarHost = { createSnackBarHost() }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                itemsIndexed(state.experiences) { index, experience ->
                    ExperienceItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        experience = experience,
                        active = index == 0,
                        horizontal = false
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
        ExperienceScreenContent(
            navController = rememberNavController(),
            state = ExperienceState(experiences = listOf(Experience.previewData()))
        )
    }
}
