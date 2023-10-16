package be.christiano.portfolio.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(ExperienceEntity.ENTITY_NAME)
data class ExperienceEntity(
    @PrimaryKey
    val id: String,
    val jobPosition: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String
) {
    companion object {
        const val ENTITY_NAME = "experience"
    }
}
