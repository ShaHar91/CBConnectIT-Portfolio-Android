package be.cbconnectit.portfolio.app.data.repository

import be.cbconnectit.portfolio.app.data.local.daos.LinkDao
import be.cbconnectit.portfolio.app.data.local.daos.TagDao
import be.cbconnectit.portfolio.app.data.local.daos.WorkDao
import be.cbconnectit.portfolio.app.data.local.daos.WorkTagCrossRefDao
import be.cbconnectit.portfolio.app.data.local.entities.WorkTagCrossRefEntity
import be.cbconnectit.portfolio.app.data.mapper.toEntities
import be.cbconnectit.portfolio.app.data.mapper.toLink
import be.cbconnectit.portfolio.app.data.mapper.toTag
import be.cbconnectit.portfolio.app.data.mapper.toWork
import be.cbconnectit.portfolio.app.data.mapper.toWorks
import be.cbconnectit.portfolio.app.data.remote.api.WorkApi
import be.cbconnectit.portfolio.app.data.utils.TransactionProvider
import be.cbconnectit.portfolio.app.domain.model.Work
import be.cbconnectit.portfolio.app.domain.repository.WorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkRepositoryImpl(
    private val workApi: WorkApi,
    private val workDao: WorkDao,
    private val tagDao: TagDao,
    private val workTagCrossRefDao: WorkTagCrossRefDao,
    private val linkDao: LinkDao,
    private val transactionProvider: TransactionProvider
) : WorkRepository {

    override suspend fun fetchAllWorks(): Result<List<Work>> {
        return try {
            val works = workApi.fetchAllWorks()
            transactionProvider.runAsTransaction {
                workDao.insertMany(works.toEntities())
                tagDao.insertMany(works.map { it.tags.toEntities() }.flatten())
                linkDao.insertMany(works.map { it.links.toEntities(it.id) }.flatten())
                val crossRefEntities = works.map { dto -> dto.tags.map { tag -> WorkTagCrossRefEntity(dto.id, tag.id) } }.flatten()
                workTagCrossRefDao.insertMany(crossRefEntities)
            }
            Result.success(works.toWorks())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllWorks(): Flow<List<Work>> = workTagCrossRefDao.findAllWorksWithTags().map { list ->
        list.map { workWithTags ->
            workWithTags.work.toWork(links = workWithTags.links.map { it.toLink() }, tags = workWithTags.tags.map { it.toTag() })
        }
    }
}