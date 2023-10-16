package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.christiano.portfolio.app.data.local.entities.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(services: List<ServiceEntity>)

    @Query("SELECT * FROM service")
    fun findAllFlow(): Flow<List<ServiceEntity>>
}