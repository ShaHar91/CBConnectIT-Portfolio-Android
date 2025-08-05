package be.cbconnectit.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import be.cbconnectit.portfolio.app.data.local.entities.CompanyEntity
import be.cbconnectit.portfolio.app.data.local.entities.ExperienceEntity
import be.cbconnectit.portfolio.app.data.local.entities.ExperienceTagCrossRefEntity
import be.cbconnectit.portfolio.app.data.local.entities.JobPositionEntity
import be.cbconnectit.portfolio.app.data.local.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExperienceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(experiences: List<ExperienceEntity>)

    // TODO demo: Transactional query to fetch all experiences with their relations
    //  in case anything goes wrong, the transaction will roll back
    @Transaction
    @Query("SELECT * FROM experience")
    fun findAllFlow(): Flow<List<ExperienceWithRelations>>
}

data class ExperienceWithRelations(
    // TODO demo: Embedded ExperienceEntity
    @Embedded
    val experience: ExperienceEntity,

    // TODO demo: Relation with CompanyEntity
    @Relation(parentColumn = "company_id", entityColumn = "id")
    val company: CompanyEntity,

    // TODO demo: Relation with JobPositionEntity
    @Relation(parentColumn = "job_position_id", entityColumn = "id")
    val jobPosition: JobPositionEntity,

    // TODO demo: Many-to-many relationship with tags
    @Relation(
        parentColumn = "id",
        entity = TagEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ExperienceTagCrossRefEntity::class,
            parentColumn = ExperienceTagCrossRefEntity.COLUMN_ID_EXPERIENCE,
            entityColumn = ExperienceTagCrossRefEntity.COLUMN_ID_TAG
        )
    )
    val tags: List<TagEntity>
)
