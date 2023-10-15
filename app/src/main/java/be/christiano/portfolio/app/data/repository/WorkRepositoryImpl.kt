package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.mapper.toWork
import be.christiano.portfolio.app.data.remote.Result
import be.christiano.portfolio.app.data.remote.api.WorkApi
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.repository.WorkRepository

class WorkRepositoryImpl(
    private val workApi: WorkApi
) : WorkRepository {
    override suspend fun fetchAllWorks(): Result<List<Work>> {
        return try {
            Result.Success(workApi.fetchAllWorks().map { it.toWork() })
        } catch (exception: Exception) {
            Result.Error("${exception.message}")
        }
    }
}