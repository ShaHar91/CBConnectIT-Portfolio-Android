package be.christiano.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import be.christiano.portfolio.app.R

enum class LayoutSystem(@StringRes val systemName: Int, @StringRes val description: Int, @DrawableRes val drawable: Int) {
    Compose(R.string.jetpack_compose, R.string.jetpack_compose_description, R.drawable.compose),
    Xml(R.string.xml_layout, R.string.xml_layout_description, R.drawable.xml)
}