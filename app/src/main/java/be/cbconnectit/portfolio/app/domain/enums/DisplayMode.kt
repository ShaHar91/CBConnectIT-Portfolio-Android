package be.cbconnectit.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import be.cbconnectit.portfolio.app.R

enum class DisplayMode(@DrawableRes val icon: Int, val options: List<Int>) {
    Light(R.drawable.ic_light, listOf(AppCompatDelegate.MODE_NIGHT_NO)),
    Dark(R.drawable.ic_dark, listOf(AppCompatDelegate.MODE_NIGHT_YES)),
    System(R.drawable.ic_theme_auto, listOf(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, AppCompatDelegate.MODE_NIGHT_UNSPECIFIED))
}