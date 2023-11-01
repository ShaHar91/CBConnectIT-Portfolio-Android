package be.cbconnectit.portfolio.app.data.repository

import be.cbconnectit.portfolio.app.data.local.daos.ServiceDao
import be.cbconnectit.portfolio.app.data.mapper.toEntities
import be.cbconnectit.portfolio.app.data.mapper.toServices
import be.cbconnectit.portfolio.app.data.remote.api.ServiceApi
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
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