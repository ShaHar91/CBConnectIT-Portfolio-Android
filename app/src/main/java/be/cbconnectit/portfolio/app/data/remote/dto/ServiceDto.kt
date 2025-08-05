package be.cbconnectit.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ServiceDto(
    val id: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("banner_image_url")
    val bannerImageUrl: String? = null,
    val title: String,
    @SerialName("short_description")
    val shortDescription: String? = null,
    val description: String,
    @SerialName("banner_description")
    val bannerDescription: String? = null,
    @SerialName("sub_services")
    val subServices: List<ServiceDto>? = null,
    @SerialName("extra_info")
    val extraInfo: String? = null,
    val tag: TagDto? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
): Parcelable
