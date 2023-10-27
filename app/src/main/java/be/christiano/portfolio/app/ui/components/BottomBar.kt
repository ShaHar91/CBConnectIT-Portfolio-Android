package be.christiano.portfolio.app.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.main.NavGraphs
import be.christiano.portfolio.app.ui.main.destinations.ComponentsScreenDestination
import be.christiano.portfolio.app.ui.main.destinations.IntroductionScreenDestination
import be.christiano.portfolio.app.ui.main.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState


@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            val isCurrentDestOnBackstack = navController.isRouteOnBackStackAsState(destination.direction).value
            NavigationBarItem(
                selected = isCurrentDestOnBackstack,
                onClick = {
                    if (isCurrentDestOnBackstack) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navController.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    navController.navigate(destination.direction) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = { Icon(painter = painterResource(id = destination.icon), contentDescription = stringResource(id = destination.label)) },
                label = { Text(text = stringResource(id = destination.label)) }
            )
        }
    }
}


enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    Introduction(
        direction = IntroductionScreenDestination,
        icon = R.drawable.ic_home,
        label = R.string.introduction
    ),
//    Components(
//        direction = ComponentsScreenDestination,
//        icon = R.drawable.ic_components,
//        label = R.string.components
//    ),
    Settings(
        direction = SettingsScreenDestination,
        icon = R.drawable.ic_settings,
        label = R.string.settings
    )
}