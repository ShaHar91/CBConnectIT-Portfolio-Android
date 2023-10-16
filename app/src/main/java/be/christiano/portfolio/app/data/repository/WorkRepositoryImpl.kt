package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.local.daos.TagDao
import be.christiano.portfolio.app.data.local.daos.WorkDao
import be.christiano.portfolio.app.data.mapper.toEntities
import be.christiano.portfolio.app.data.mapper.toTagEntity
import be.christiano.portfolio.app.data.mapper.toTags
import be.christiano.portfolio.app.data.mapper.toWork
import be.christiano.portfolio.app.data.mapper.toWorkEntity
import be.christiano.portfolio.app.data.mapper.toWorks
import be.christiano.portfolio.app.data.remote.api.WorkApi
import be.christiano.portfolio.app.data.utils.TransactionProvider
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.repository.WorkRepository
import kotlinx.coroutines.flow.map

class WorkRepositoryImpl(
    private val workApi: WorkApi,
    private val workDao: WorkDao,
    private val tagDao: TagDao,
//    private val workTagCrossRefDao: WorkTagCrossRefDao,
    private val transactionProvider: TransactionProvider
) : WorkRepository {

    override suspend fun fetchAllWorks(): Result<List<Work>> {
        return try {
            val works = workApi.fetchAllWorks()
            transactionProvider.runAsTransaction {
                workDao.insertMany(works.toEntities())
                tagDao.insertMany(works.map { it.tags.toEntities()}.flatten())
            }
            //TODO: also save the tags to a separate dao!
            Result.success(works.toWorks())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllWorks() = workDao.findAllFlow().map { it.toWorks() }
}