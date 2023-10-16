package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Work
import kotlinx.coroutines.flow.Flow

interface WorkRepository {
    suspend fun fetchAllWorks() : Result<List<Work>>

    fun findAllWorks(): Flow<List<Work>>
}