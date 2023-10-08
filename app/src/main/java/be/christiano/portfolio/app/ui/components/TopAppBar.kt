package be.christiano.portfolio.app.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@ExperimentalMaterial3Api
@Composable
fun DefaultAppBar(
    navController: NavController,
    appBarTitle: String,
    overrideBackArrow: (() -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
    title: @Composable () -> Unit = { DefaultAppBarText(appBarTitle = appBarTitle) },
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        colors = colors,
        title = title,
        actions = actions,
        navigationIcon = { DefaultNavigationIcon(navController = navController, overrideBackArrow = overrideBackArrow) })
}

@Composable
private fun DefaultAppBarText(
    appBarTitle: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Text(
        text = appBarTitle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = textStyle
    )
}

@Composable
private fun DefaultNavigationIcon(navController: NavController, overrideBackArrow: (() -> Unit)? = null) {
    if (navController.previousBackStackEntry != null || overrideBackArrow != null) {
        IconButton(onClick = {
            if (overrideBackArrow != null) {
                overrideBackArrow.invoke()
                return@IconButton
            }

            navController.navigateUp()
        }) {
            Icon(Icons.Default.ArrowBack, "Navigation icon")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppBarPreview() {
    PortfolioTheme {
        DefaultAppBar(navController = rememberNavController(), "Title", overrideBackArrow = {}) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Delete, contentDescription = "")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }
        }
    }
}