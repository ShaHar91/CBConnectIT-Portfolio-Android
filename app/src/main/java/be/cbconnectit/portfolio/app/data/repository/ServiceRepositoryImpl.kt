package be.cbconnectit.portfolio.app.data.repository

import android.util.Log
import be.cbconnectit.portfolio.app.data.local.daos.ServiceDao
import be.cbconnectit.portfolio.app.data.local.daos.TagDao
import be.cbconnectit.portfolio.app.data.mapper.toEntities
import be.cbconnectit.portfolio.app.data.mapper.toService
import be.cbconnectit.portfolio.app.data.mapper.toServices
import be.cbconnectit.portfolio.app.data.mapper.toTag
import be.cbconnectit.portfolio.app.data.mapper.toTagEntity
import be.cbconnectit.portfolio.app.data.remote.api.ServiceApi
import be.cbconnectit.portfolio.app.data.utils.TransactionProvider
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceRepositoryImpl(
    private val serviceApi: ServiceApi,
    private val serviceDao: ServiceDao,
    private val tagDao: TagDao,
    private val transactionProvider: TransactionProvider
) : ServiceRepository {

    override suspend fun fetchAllServices(): Result<List<Service>> {
        return try {
            val services = serviceApi.fetchAllServices()

            transactionProvider.runAsTransaction {
                serviceDao.insertMany(services.toEntities())

                val tags = services.flatMap { parentService ->
                    listOfNotNull(parentService.tag?.toTagEntity())
                        .plus(parentService.subServices.orEmpty().mapNotNull { it.tag?.toTagEntity() })
                }
                tagDao.insertMany(tags)
            }

            Result.success(services.toServices())
        } catch (exception: Exception) {
            Log.d("TAG", "fetchAllServices: $exception")
            Result.failure(exception)
        }
    }

    override fun findAllServices(parentServiceId: String?): Flow<List<Service>> =
        serviceDao.findAllFlow(parentServiceId)
            .map {
                it.map { serviceWithTags ->
                    serviceWithTags.service.toService().copy(tag = serviceWithTags.tag?.toTag())
                }
            }

    override fun findParentServiceName(parentServiceId: String): Flow<Service?> =
        serviceDao.findById(parentServiceId)
            .map { serviceWithTags ->
                serviceWithTags?.service?.toService()?.copy(tag = serviceWithTags.tag?.toTag())
            }
}