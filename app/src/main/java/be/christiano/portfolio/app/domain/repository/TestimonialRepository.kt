package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Testimonial

interface TestimonialRepository {
    suspend fun fetchAllTestimonials(): Result<List<Testimonial>>
}