package be.cbconnectit.portfolio.app.data.remote.dto

import android.os.Parcelable
import be.cbconnectit.portfolio.app.domain.enums.TechStack
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ExperienceDto(
    val id: String,
    @SerialName("job_position")
    val jobPosition: String,
    @SerialName("short_description")
    val shortDescription: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String,
    @SerialName("tech_stack")
    val techStacks: List<TechStack>
): Parcelable