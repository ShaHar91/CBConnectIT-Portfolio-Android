package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.local.daos.TestimonialDao
import be.christiano.portfolio.app.data.mapper.toEntities
import be.christiano.portfolio.app.data.mapper.toTestimonials
import be.christiano.portfolio.app.data.remote.api.TestimonialApi
import be.christiano.portfolio.app.domain.model.Testimonial
import be.christiano.portfolio.app.domain.repository.TestimonialRepository
import kotlinx.coroutines.flow.map

class TestimonialRepositoryImpl(
    private val testimonialApi: TestimonialApi,
    private val testimonialDao: TestimonialDao
) : TestimonialRepository {

    override suspend fun fetchAllTestimonials(): Result<List<Testimonial>> {
        return try {
            val testimonials = testimonialApi.fetchAllTestimonials()
            testimonialDao.insertMany(testimonials.toEntities())
            Result.success(testimonials.toTestimonials())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun findAllTestimonials() = testimonialDao.findAllFlow().map { it.toTestimonials() }
}