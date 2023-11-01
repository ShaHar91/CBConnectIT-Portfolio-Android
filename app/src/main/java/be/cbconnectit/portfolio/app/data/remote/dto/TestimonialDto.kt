package be.cbconnectit.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TestimonialDto(
    val id: String,
    val image: String,
    @SerialName("full_name")
    val fullName: String,
    val function: String,
    val review: String
) : Parcelable
