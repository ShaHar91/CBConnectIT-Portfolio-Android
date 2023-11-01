package be.cbconnectit.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import be.cbconnectit.portfolio.app.R

enum class LinkType(@DrawableRes val iconRes: Int) {
    Github(R.drawable.ic_github),
    LinkedIn(R.drawable.ic_linkedin),
    PlayStore(R.drawable.ic_google_play)
}