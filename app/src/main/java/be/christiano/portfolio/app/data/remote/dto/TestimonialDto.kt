package be.christiano.portfolio.app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TestimonialDto(
    val id: String,
    val image: String,
    val fullName: String,
    val function: String,
    val review: String
) : Parcelable
