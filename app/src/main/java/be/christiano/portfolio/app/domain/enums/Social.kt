package be.christiano.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R

enum class Social(val link: String, val type: LinkType) {
    Github("https://github.com/ShaHar91", LinkType.Github),
    LinkedIn("https://www.linkedin.com/in/christiano-bolla/", LinkType.LinkedIn)
}