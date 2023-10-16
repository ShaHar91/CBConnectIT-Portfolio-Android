package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Work

interface WorkRepository {
    suspend fun fetchAllWorks() : Result<List<Work>>
}