package be.cbconnectit.portfolio.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(ServiceEntity.ENTITY_NAME)
data class ServiceEntity(
    @PrimaryKey
    val id: String,
    val image: String,
    val title: String,
    val description: String
) {
    companion object {
        const val ENTITY_NAME = "service"
    }
}
