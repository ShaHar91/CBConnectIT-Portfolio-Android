package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ServiceDto(
    val id: String,
    val image: String,
    val title: String,
    val description: String
): Parcelable
