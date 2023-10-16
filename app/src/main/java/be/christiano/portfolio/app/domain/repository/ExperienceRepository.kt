package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Experience
import kotlinx.coroutines.flow.Flow

interface ExperienceRepository {
    suspend fun fetchAllExperiences(): Result<List<Experience>>

    fun findAllExperiences(): Flow<List<Experience>>
}