package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import be.christiano.portfolio.app.data.local.entities.WorkTagCrossRefEntity
import be.christiano.portfolio.app.data.local.entities.WorkWithTags

@Dao
interface WorkTagCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(crossRefList: List<WorkTagCrossRefEntity>)

    @Transaction
    @Query("SELECT * FROM work")
    suspend fun findAllWorksWithTags(): List<WorkWithTags>
}