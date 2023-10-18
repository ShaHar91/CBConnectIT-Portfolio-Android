package be.christiano.portfolio.app.domain.enums

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R
import kotlinx.serialization.Serializable

enum class TechStack(@DrawableRes val iconRes: Int){
    Android(R.drawable.ic_phone_android),
    AndroidTV(R.drawable.ic_tv_android)
}
