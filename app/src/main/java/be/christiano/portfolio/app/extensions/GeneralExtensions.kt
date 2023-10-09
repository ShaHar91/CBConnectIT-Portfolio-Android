package be.christiano.portfolio.app.extensions

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Context.startWeb(url: String, @ColorInt toolbarColor: Int = Color.Black.toArgb(), @ColorInt navigationBarColor: Int = toolbarColor) {
    val customTabColorSchemeParams = CustomTabColorSchemeParams.Builder()
        .setNavigationBarColor(navigationBarColor)
        .setToolbarColor(toolbarColor)
        .build()

    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabColorSchemeParams)
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
        .build()

    var launchUrl = url
    if (!launchUrl.startsWith("http")) {
        launchUrl = "http://$url"
    }
    customTabsIntent.launchUrl(this, Uri.parse(launchUrl))
}