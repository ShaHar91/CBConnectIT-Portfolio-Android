package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.local.daos.ServiceDao
import be.christiano.portfolio.app.data.mapper.toEntities
import be.christiano.portfolio.app.data.mapper.toService
import be.christiano.portfolio.app.data.mapper.toServiceEntity
import be.christiano.portfolio.app.data.mapper.toServices
import be.christiano.portfolio.app.data.remote.api.ServiceApi
import be.christiano.portfolio.app.domain.model.Service
import be.christiano.portfolio.app.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.map

class ServiceRepositoryImpl(
    private val serviceApi: ServiceApi,
    private val serviceDao: ServiceDao
) : ServiceRepository {

    override suspend fun fetchAllServices(): Result<List<Service>> {
        return try {
            val services = serviceApi.fetchAllServices()
            serviceDao.insertMany(services.toEntities())
            Result.success(services.toServices())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllServices() = serviceDao.findAllFlow().map { it.toServices() }
}