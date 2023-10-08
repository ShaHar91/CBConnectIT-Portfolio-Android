package be.christiano.portfolio.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import be.christiano.portfolio.app.ui.components.BottomBar
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

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

        setContent {
            PortfolioTheme {
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