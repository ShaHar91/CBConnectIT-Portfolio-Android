package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.local.daos.ExperienceDao
import be.christiano.portfolio.app.data.mapper.toEntities
import be.christiano.portfolio.app.data.mapper.toExperiences
import be.christiano.portfolio.app.data.remote.api.ExperienceApi
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.repository.ExperienceRepository
import kotlinx.coroutines.flow.map

class ExperienceRepositoryImpl(
    private val experienceApi: ExperienceApi,
    private val experienceDao: ExperienceDao
) : ExperienceRepository {


    override suspend fun fetchAllExperiences(): Result<List<Experience>> {
        return try {
            val experiences = experienceApi.fetchAllExperiences()
            experienceDao.insertMany(experiences.toEntities())
            Result.success(experiences.toExperiences())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllExperiences() = experienceDao.findAllFlow().map { it.toExperiences() }
}