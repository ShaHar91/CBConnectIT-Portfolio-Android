package be.christiano.portfolio.app.data.models

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R

enum class Service(
    @DrawableRes val icon: Int,
    val imageDesc: String,
    val title: String,
    val description: String
) {
    Android(
        icon = R.drawable.android_img,
        imageDesc = "Android Icon",
        title = "Android Development",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    Tutoring(
        icon = R.drawable.tutoring_img,
        imageDesc = "Tutoring Icon",
        title = "Tutoring",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    Teamwork(
        icon = R.drawable.teamwork_img,
        imageDesc = "Teamwork Icon",
        title = "Teamwork",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
}