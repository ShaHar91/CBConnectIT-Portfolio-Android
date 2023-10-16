package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.christiano.portfolio.app.data.local.entities.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(works: List<WorkEntity>)

    @Query("SELECT * FROM work")
    fun findAllFlow(): Flow<List<WorkEntity>>
}