package be.christiano.portfolio.app.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
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

/**
 * This function will open a chooser to send an email
 *
 * @param email An email address to send a mail to
 * @param chooserTitle The title for the chooser
 * @param error A callback function when an error occurs. Mostly because of no available application that can send a mail
 */
fun Context.startIntentMail(email: String, chooserTitle: String, error: () -> Unit) {
    try {
        val emails = arrayOf(email)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, emails)
        startActivity(Intent.createChooser(intent, chooserTitle))
    } catch (ex: Exception) {
        ex.printStackTrace()
        error()
    }
}
