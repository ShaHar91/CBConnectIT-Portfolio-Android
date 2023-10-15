package be.christiano.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R

enum class Social(val link: String, @DrawableRes val icon: Int) {
    Github("https://github.com/ShaHar91", R.drawable.ic_github),
    LinkedIn("https://www.linkedin.com/in/christiano-bolla/", R.drawable.ic_linkedin)
}