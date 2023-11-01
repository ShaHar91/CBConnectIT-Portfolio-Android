package be.cbconnectit.portfolio.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.cbconnectit.portfolio.app.data.local.daos.ExperienceDao
import be.cbconnectit.portfolio.app.data.local.daos.LinkDao
import be.cbconnectit.portfolio.app.data.local.daos.ServiceDao
import be.cbconnectit.portfolio.app.data.local.daos.TagDao
import be.cbconnectit.portfolio.app.data.local.daos.TestimonialDao
import be.cbconnectit.portfolio.app.data.local.daos.WorkDao
import be.cbconnectit.portfolio.app.data.local.daos.WorkTagCrossRefDao
import be.cbconnectit.portfolio.app.data.local.entities.ExperienceEntity
import be.cbconnectit.portfolio.app.data.local.entities.LinkEntity
import be.cbconnectit.portfolio.app.data.local.entities.ServiceEntity
import be.cbconnectit.portfolio.app.data.local.entities.TagEntity
import be.cbconnectit.portfolio.app.data.local.entities.TestimonialEntity
import be.cbconnectit.portfolio.app.data.local.entities.WorkEntity
import be.cbconnectit.portfolio.app.data.local.entities.WorkTagCrossRefEntity
import be.cbconnectit.portfolio.app.data.local.utils.Converters

@Database(
    entities = [
        ExperienceEntity::class,
        ServiceEntity::class,
        TestimonialEntity::class,
        WorkEntity::class,
        TagEntity::class,
        WorkTagCrossRefEntity::class,
        LinkEntity::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PortfolioDatabase : RoomDatabase() {

    abstract val experienceDao: ExperienceDao
    abstract val testimonialDao: TestimonialDao
    abstract val serviceDao: ServiceDao
    abstract val workDao: WorkDao
    abstract val tagDao: TagDao
    abstract val workTagCrossRefDao: WorkTagCrossRefDao
    abstract val linkDao: LinkDao
}