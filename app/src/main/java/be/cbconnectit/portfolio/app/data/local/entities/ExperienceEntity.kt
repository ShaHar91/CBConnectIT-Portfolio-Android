package be.cbconnectit.portfolio.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.cbconnectit.portfolio.app.domain.enums.TechStack

@Entity(ExperienceEntity.ENTITY_NAME)
data class ExperienceEntity(
    @PrimaryKey
    val id: String,
    val jobPosition: String,
    val shortDescription: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String,
    val techStacks: List<TechStack>
) {
    companion object {
        const val ENTITY_NAME = "experience"
    }
}
