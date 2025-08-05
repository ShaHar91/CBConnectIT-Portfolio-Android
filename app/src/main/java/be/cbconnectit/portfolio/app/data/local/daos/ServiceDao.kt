package be.cbconnectit.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import be.cbconnectit.portfolio.app.data.local.entities.ServiceEntity
import be.cbconnectit.portfolio.app.data.local.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(services: List<ServiceEntity>)

    @Transaction
    @Query("SELECT * FROM service WHERE parent_service_id IS :parentServiceId")
    fun findAllFlow(parentServiceId: String? = null): Flow<List<ServiceWithTags>>

    @Transaction
    @Query("SELECT * FROM service WHERE id IS :parentServiceId")
    fun findById(parentServiceId: String): Flow<ServiceWithTags?>
}

// Service Entity with relation of Tag Entity
data class ServiceWithTags(
    @Embedded
    val service: ServiceEntity,
    @Relation(parentColumn = "tagId", entityColumn = "id")
    val tag: TagEntity?
)
