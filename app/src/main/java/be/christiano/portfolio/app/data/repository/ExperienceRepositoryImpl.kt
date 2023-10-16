package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.mapper.toExperience
import be.christiano.portfolio.app.data.remote.api.ExperienceApi
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.repository.ExperienceRepository

class ExperienceRepositoryImpl(private val experienceApi: ExperienceApi) : ExperienceRepository {

    override suspend fun fetchAllExperiences(): Result<List<Experience>> {
        return try {
            Result.success(experienceApi.fetchAllExperiences().map { it.toExperience() })
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}