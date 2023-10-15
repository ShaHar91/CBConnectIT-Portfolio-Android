package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WorkDto(
    val id: String,
    val image: String,
    val title: String,
    val description: String,
    val link: String,
    val tags: List<TagDto>
) : Parcelable

@Parcelize
@Serializable
data class TagDto(
    val id: String,
    val name: String
) : Parcelable