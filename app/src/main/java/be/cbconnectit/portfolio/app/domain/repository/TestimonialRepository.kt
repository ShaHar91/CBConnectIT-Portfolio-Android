package be.cbconnectit.portfolio.app.domain.repository

import be.cbconnectit.portfolio.app.domain.model.Testimonial
import kotlinx.coroutines.flow.Flow

interface TestimonialRepository {
    suspend fun fetchAllTestimonials(): Result<List<Testimonial>>
    fun findAllTestimonials(): Flow<List<Testimonial>>
}