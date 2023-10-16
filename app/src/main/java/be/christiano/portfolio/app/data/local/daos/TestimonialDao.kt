package be.christiano.portfolio.app.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.christiano.portfolio.app.data.local.entities.TestimonialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestimonialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(testimonials: List<TestimonialEntity>)

    @Query("SELECT * FROM testimonial")
    fun findAllFlow(): Flow<List<TestimonialEntity>>
}