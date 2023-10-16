package be.christiano.portfolio.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(TestimonialEntity.ENTITY_NAME)
data class TestimonialEntity(
    @PrimaryKey
    val id: String,
    val image: String,
    val fullName: String,
    val function: String,
    val review: String
) {
    companion object {
        const val ENTITY_NAME = "testimonial"
    }
}
