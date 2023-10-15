package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.mapper.toService
import be.christiano.portfolio.app.data.remote.Result
import be.christiano.portfolio.app.data.remote.api.ServiceApi
import be.christiano.portfolio.app.domain.model.Service
import be.christiano.portfolio.app.domain.repository.ServiceRepository

class ServiceRepositoryImpl(private val serviceApi: ServiceApi) : ServiceRepository {

    override suspend fun fetchAllServices(): Result<List<Service>> {
        return try {
            Result.Success(serviceApi.fetchAllServices().map { it.toService() })
        } catch (exception: Exception) {
            Result.Error("${exception.message}")
        }
    }
}