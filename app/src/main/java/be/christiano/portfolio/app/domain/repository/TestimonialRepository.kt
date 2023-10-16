package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Testimonial
import kotlinx.coroutines.flow.Flow

interface TestimonialRepository {
    suspend fun fetchAllTestimonials(): Result<List<Testimonial>>
    fun findAllTestimonials(): Flow<List<Testimonial>>
}