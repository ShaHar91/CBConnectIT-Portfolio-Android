package be.cbconnectit.portfolio.app.domain.repository

import be.cbconnectit.portfolio.app.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun fetchAllServices(): Result<List<Service>>

    fun findAllServices(): Flow<List<Service>>
}