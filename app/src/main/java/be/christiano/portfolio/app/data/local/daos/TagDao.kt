package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.christiano.portfolio.app.data.local.entities.TagEntity
import be.christiano.portfolio.app.data.local.entities.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(items: List<TagEntity>)

    @Query("SELECT * FROM tag")
    fun findAllFlow(): Flow<List<TagEntity>>
}