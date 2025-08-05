package be.cbconnectit.portfolio.app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(TestimonialEntity.ENTITY_NAME)
data class TestimonialEntity(
    @PrimaryKey
    val id: String = "",
    @ColumnInfo("image_url")
    val imageUrl: String = "",
    @ColumnInfo("full_name")
    val fullName: String = "",
    @ColumnInfo("company_id")
    val companyId: String? = null,
    @ColumnInfo("job_position_id")
    val jobPositionId: String? = null,
    val review: String = "",
    @ColumnInfo("created_at")
    val createdAt: String = "",
    @ColumnInfo("updated_at")
    val updatedAt: String = ""
) {
    companion object {
        const val ENTITY_NAME = "testimonial"
    }
}
