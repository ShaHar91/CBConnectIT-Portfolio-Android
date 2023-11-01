package be.cbconnectit.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import be.cbconnectit.portfolio.app.R

enum class TechStack(@DrawableRes val iconRes: Int){
    Android(R.drawable.ic_phone_android),
    AndroidTV(R.drawable.ic_tv_android)
}
