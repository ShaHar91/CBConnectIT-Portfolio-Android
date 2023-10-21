package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import be.christiano.portfolio.app.domain.enums.LinkType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LinkDto(
    val type: LinkType,
    val url: String
) : Parcelable

