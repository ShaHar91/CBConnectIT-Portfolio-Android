package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Service

interface ServiceRepository {
    suspend fun fetchAllServices(): Result<List<Service>>
}