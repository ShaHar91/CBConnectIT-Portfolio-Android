package be.cbconnectit.portfolio.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import be.cbconnectit.portfolio.app.ui.components.BottomBar
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainComposeActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainComposeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModel<MainViewModel>()

        setContent {
            val state by viewModel.state.collectAsState()


            val isDarkTheme = when (state.displayMode) {
                AppCompatDelegate.MODE_NIGHT_NO -> false
                AppCompatDelegate.MODE_NIGHT_YES -> true
                else -> isSystemInDarkTheme()
            }
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(Color.Red.toArgb(), Color.Blue.toArgb(), { isDarkTheme })
            )

            PortfolioTheme(
                darkTheme = isDarkTheme,
                dynamicColor = state.dynamicModeEnabled
            ) {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ) {
            Box {
                // This box is used to add a filled background color to the status bar area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(it.calculateTopPadding())
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                        .align(Alignment.TopCenter)
                )

                DestinationsNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    navGraph = NavGraphs.root,
                    engine = engine,
                    navController = navController,
                )
            }
        }
    }
}