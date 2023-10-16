package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.christiano.portfolio.app.data.local.entities.ExperienceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExperienceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(experiences: List<ExperienceEntity>)

    @Query("SELECT * FROM experience")
    fun findAllFlow(): Flow<List<ExperienceEntity>>
}