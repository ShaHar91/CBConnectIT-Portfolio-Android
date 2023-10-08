package be.christiano.portfolio.app.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import be.christiano.portfolio.app.ui.components.DefaultAppBar
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ComponentsScreen(
    navController: NavController,
) {

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { DefaultAppBar(navController = navController, appBarTitle = "Components") }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}