package be.cbconnectit.portfolio.app.data.repository

import be.cbconnectit.portfolio.app.data.local.daos.CompanyDao
import be.cbconnectit.portfolio.app.data.local.daos.ExperienceDao
import be.cbconnectit.portfolio.app.data.local.daos.ExperienceTagCrossRefDao
import be.cbconnectit.portfolio.app.data.local.daos.JobPositionDao
import be.cbconnectit.portfolio.app.data.local.daos.LinkDao
import be.cbconnectit.portfolio.app.data.local.daos.TagDao
import be.cbconnectit.portfolio.app.data.local.entities.CompanyEntity
import be.cbconnectit.portfolio.app.data.local.entities.ExperienceTagCrossRefEntity
import be.cbconnectit.portfolio.app.data.local.entities.JobPositionEntity
import be.cbconnectit.portfolio.app.data.local.entities.LinkEntity
import be.cbconnectit.portfolio.app.data.local.entities.TagEntity
import be.cbconnectit.portfolio.app.data.mapper.toCompany
import be.cbconnectit.portfolio.app.data.mapper.toCompanyEntity
import be.cbconnectit.portfolio.app.data.mapper.toEntities
import be.cbconnectit.portfolio.app.data.mapper.toExperience
import be.cbconnectit.portfolio.app.data.mapper.toExperiences
import be.cbconnectit.portfolio.app.data.mapper.toJobPosition
import be.cbconnectit.portfolio.app.data.mapper.toJobPositionEntity
import be.cbconnectit.portfolio.app.data.mapper.toLinkEntity
import be.cbconnectit.portfolio.app.data.mapper.toTag
import be.cbconnectit.portfolio.app.data.mapper.toTagEntity
import be.cbconnectit.portfolio.app.data.remote.api.ExperienceApi
import be.cbconnectit.portfolio.app.data.utils.TransactionProvider
import be.cbconnectit.portfolio.app.domain.model.Experience
import be.cbconnectit.portfolio.app.domain.repository.ExperienceRepository
import kotlinx.coroutines.flow.map

class ExperienceRepositoryImpl(
    private val experienceApi: ExperienceApi,
    private val experienceDao: ExperienceDao,
    private val companyDao: CompanyDao,
    private val jobPositionDao: JobPositionDao,
    private val linkDao: LinkDao,
    private val tagDao: TagDao,
    private val experienceTagCrossRefDao: ExperienceTagCrossRefDao,
    private val transactionProvider: TransactionProvider
) : ExperienceRepository {


    override suspend fun fetchAllExperiences(): Result<List<Experience>> {
        return try {
            val experiences = experienceApi.fetchAllExperiences()

            val links = mutableListOf<LinkEntity>()
            val companies = mutableListOf<CompanyEntity>()
            val jobPositions = mutableListOf<JobPositionEntity>()
            val tags = mutableListOf<TagEntity>()
            val experienceTagCrossRefs = mutableListOf<ExperienceTagCrossRefEntity>()

            // Trying out something to iterate only once over the items and then mapping everything to the data that we might need.
            // This might be overkill for this simple application, but thinking about it makes me wonder about optimizations.
            experiences.forEach { item ->
                links.addAll(item.company.links.map { it.toLinkEntity() })
                companies.add(item.company.toCompanyEntity())
                jobPositions.add(item.jobPosition.toJobPositionEntity())

                item.tags.map {
                    tags.add(it.toTagEntity())
                    experienceTagCrossRefs.add(ExperienceTagCrossRefEntity(item.id, it.id))
                }
            }

            transactionProvider.runAsTransaction {
                linkDao.insertMany(links)
                companyDao.insertMany(companies)
                jobPositionDao.insertMany(jobPositions)
                tagDao.insertMany(tags)
                experienceTagCrossRefDao.insertMany(experienceTagCrossRefs)
                experienceDao.insertMany(experiences.toEntities())
            }

            Result.success(experiences.toExperiences())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllExperiences() = experienceDao.findAllFlow().map { list ->
        list.map { itemWithRelations ->
            val company = itemWithRelations.company.toCompany()
            val jobPosition = itemWithRelations.jobPosition.toJobPosition()
            val tags = itemWithRelations.tags.map { it.toTag() }

            itemWithRelations.experience.toExperience().copy(company = company, jobPosition = jobPosition, tags = tags)
        }
    }
}