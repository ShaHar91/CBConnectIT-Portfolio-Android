package be.cbconnectit.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import be.cbconnectit.portfolio.app.data.local.entities.CompanyEntity
import be.cbconnectit.portfolio.app.data.local.entities.JobPositionEntity
import be.cbconnectit.portfolio.app.data.local.entities.TestimonialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestimonialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(testimonials: List<TestimonialEntity>)

    @Transaction
    @Query("SELECT * FROM testimonial")
    fun findAllFlow(): Flow<List<TestimonialWithRelations>>
}

// Testimonial Entity with relation of Company and JobPosition
data class TestimonialWithRelations(
    @Embedded
    val testimonial: TestimonialEntity,
    @Relation(parentColumn = "company_id", entityColumn = "id")
    val company: CompanyEntity?,
    @Relation(parentColumn = "job_position_id", entityColumn = "id")
    val jobPosition: JobPositionEntity,
)
