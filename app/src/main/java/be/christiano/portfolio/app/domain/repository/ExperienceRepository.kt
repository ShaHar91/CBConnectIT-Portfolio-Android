package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Experience

interface ExperienceRepository {
    suspend fun fetchAllExperiences(): Result<List<Experience>>
}