package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ExperienceDto(
    val id: String,
    val jobPosition: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String
): Parcelable
