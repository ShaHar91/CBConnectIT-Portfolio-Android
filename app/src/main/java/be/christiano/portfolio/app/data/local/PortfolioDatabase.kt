package be.christiano.portfolio.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import be.christiano.portfolio.app.data.local.daos.ExperienceDao
import be.christiano.portfolio.app.data.local.daos.ServiceDao
import be.christiano.portfolio.app.data.local.daos.TagDao
import be.christiano.portfolio.app.data.local.daos.TestimonialDao
import be.christiano.portfolio.app.data.local.daos.WorkDao
import be.christiano.portfolio.app.data.local.daos.WorkTagCrossRefDao
import be.christiano.portfolio.app.data.local.entities.ExperienceEntity
import be.christiano.portfolio.app.data.local.entities.ServiceEntity
import be.christiano.portfolio.app.data.local.entities.TagEntity
import be.christiano.portfolio.app.data.local.entities.TestimonialEntity
import be.christiano.portfolio.app.data.local.entities.WorkEntity
import be.christiano.portfolio.app.data.local.entities.WorkTagCrossRefEntity

@Database(
    entities = [
        ExperienceEntity::class,
        ServiceEntity::class,
        TestimonialEntity::class,
        WorkEntity::class,
        TagEntity::class,
        WorkTagCrossRefEntity::class
    ],
    version = 1
)
abstract class PortfolioDatabase : RoomDatabase() {

    abstract val experienceDao: ExperienceDao
    abstract val testimonialDao: TestimonialDao
    abstract val serviceDao: ServiceDao
    abstract val workDao: WorkDao
    abstract val tagDao: TagDao
    abstract val workTagCrossRefDao: WorkTagCrossRefDao
}