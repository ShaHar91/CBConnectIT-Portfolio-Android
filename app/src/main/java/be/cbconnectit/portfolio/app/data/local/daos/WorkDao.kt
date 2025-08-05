package be.cbconnectit.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import be.cbconnectit.portfolio.app.data.local.entities.WorkEntity
import be.cbconnectit.portfolio.app.data.local.entities.WorkWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(works: List<WorkEntity>)

    @Query("SELECT * FROM work")
    fun findAllFlow(): Flow<List<WorkEntity>>

    @Transaction
    @Query("SELECT * FROM work ORDER BY updated_at DESC")
    fun findAllWorksWithTags(): Flow<List<WorkWithTags>>
}