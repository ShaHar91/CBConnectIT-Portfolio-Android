package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import be.christiano.portfolio.app.domain.model.Link
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WorkDto(
    val id: String,
    @SerialName("banner_image")
    val bannerImage: String,
    val image: String,
    val title: String,
    @SerialName("short_description")
    val shortDescription: String,
    val description: String,
    val links: List<LinkDto>,
    val tags: List<TagDto>
) : Parcelable

@Parcelize
@Serializable
data class TagDto(
    val id: String,
    val name: String
) : Parcelable