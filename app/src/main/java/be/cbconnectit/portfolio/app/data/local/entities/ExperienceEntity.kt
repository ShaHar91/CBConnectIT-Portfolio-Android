package be.cbconnectit.portfolio.app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    ExperienceEntity.ENTITY_NAME, foreignKeys = [
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["company_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JobPositionEntity::class,
            parentColumns = ["id"],
            childColumns = ["job_position_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExperienceEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("short_description")
    val shortDescription: String,
    val description: String,
    val from: String,
    val to: String,
    @ColumnInfo("as_freelance")
    val asFreelance: Boolean,
    @ColumnInfo("job_position_id")
    val jobPositionId: String,
    @ColumnInfo("company_id")
    val companyId: String,
    @ColumnInfo("created_at")
    val createdAt: String,
    @ColumnInfo("updated_at")
    val updatedAt: String
) {
    companion object {
        const val ENTITY_NAME = "experience"
    }
}
