package be.christiano.portfolio.app.data.repository

import be.christiano.portfolio.app.data.mapper.toTestimonial
import be.christiano.portfolio.app.data.remote.Result
import be.christiano.portfolio.app.data.remote.api.TestimonialApi
import be.christiano.portfolio.app.domain.model.Testimonial
import be.christiano.portfolio.app.domain.repository.TestimonialRepository

class TestimonialRepositoryImpl(
    private val testimonialApi: TestimonialApi
) : TestimonialRepository {
    override suspend fun fetchAllTestimonials(): Result<List<Testimonial>> {
        return try {
            Result.Success(testimonialApi.fetchAllTestimonials().map { it.toTestimonial() })
        } catch (exception: Exception) {
            Result.Error("${exception.message}")
        }
    }
}