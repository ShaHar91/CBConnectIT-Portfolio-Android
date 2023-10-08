package be.christiano.portfolio.app.ui.main.introduction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import be.christiano.portfolio.app.ui.main.destinations.DummyScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate

@RootNavGraph(start = true)
@Destination
@Composable
fun IntroductionScreen(
    navController: NavController,
) {

    Column {
    Text(text = "Introduction")
        Button(onClick = {
            navController.navigate(DummyScreenDestination())
        }) {
            Text(text = "Something")
        }
    }
}


@Destination
@Composable
fun DummyScreen(
    navController: NavController
) {
    Text(text = "Dummy")
}