package be.cbconnectit.portfolio.app.data.repository

import be.cbconnectit.portfolio.app.data.local.daos.CompanyDao
import be.cbconnectit.portfolio.app.data.local.daos.JobPositionDao
import be.cbconnectit.portfolio.app.data.local.daos.LinkDao
import be.cbconnectit.portfolio.app.data.local.daos.TestimonialDao
import be.cbconnectit.portfolio.app.data.local.entities.CompanyEntity
import be.cbconnectit.portfolio.app.data.local.entities.JobPositionEntity
import be.cbconnectit.portfolio.app.data.local.entities.LinkEntity
import be.cbconnectit.portfolio.app.data.mapper.toCompany
import be.cbconnectit.portfolio.app.data.mapper.toCompanyEntity
import be.cbconnectit.portfolio.app.data.mapper.toEntities
import be.cbconnectit.portfolio.app.data.mapper.toJobPosition
import be.cbconnectit.portfolio.app.data.mapper.toJobPositionEntity
import be.cbconnectit.portfolio.app.data.mapper.toLinkEntity
import be.cbconnectit.portfolio.app.data.mapper.toTestimonial
import be.cbconnectit.portfolio.app.data.mapper.toTestimonials
import be.cbconnectit.portfolio.app.data.remote.api.TestimonialApi
import be.cbconnectit.portfolio.app.data.utils.TransactionProvider
import be.cbconnectit.portfolio.app.domain.model.Testimonial
import be.cbconnectit.portfolio.app.domain.repository.TestimonialRepository
import kotlinx.coroutines.flow.map

class TestimonialRepositoryImpl(
    private val testimonialApi: TestimonialApi,
    private val testimonialDao: TestimonialDao,
    private val companyDao: CompanyDao,
    private val linkDao: LinkDao,
    private val jobPositionDao: JobPositionDao,
    private val transactionProvider: TransactionProvider
) : TestimonialRepository {

    override suspend fun fetchAllTestimonials(): Result<List<Testimonial>> {
        return try {
            val testimonials = testimonialApi.fetchAllTestimonials()

            val links = mutableListOf<LinkEntity>()
            val companies = mutableListOf<CompanyEntity>()
            val jobPositions = mutableListOf<JobPositionEntity>()

            testimonials.forEach { item ->
                item.company?.links?.map { it.toLinkEntity() }?.let(links::addAll)
                item.company?.toCompanyEntity()?.let(companies::add)
                jobPositions.add(item.jobPosition.toJobPositionEntity())
            }

            // TODO demo: Transactionally insert testimonials and their relations into the database
            //  This ensures that if any part of the transaction fails, all changes are rolled back
            transactionProvider.runAsTransaction {
                linkDao.insertMany(links)
                companyDao.insertMany(companies)
                jobPositionDao.insertMany(jobPositions)
                testimonialDao.insertMany(testimonials.toEntities())
            }

            Result.success(testimonials.toTestimonials())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllTestimonials() = testimonialDao.findAllFlow().map { list ->
        list.map { testimonialWithRelations ->
            val company = testimonialWithRelations.company?.toCompany()
            val jobPosition = testimonialWithRelations.jobPosition.toJobPosition()
            testimonialWithRelations.testimonial.toTestimonial().copy(company = company, jobPosition = jobPosition)
        }
    }
}