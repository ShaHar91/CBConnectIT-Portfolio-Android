package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.remote.dto.TestimonialDto
import be.christiano.portfolio.app.domain.model.Testimonial

fun TestimonialDto.toTestimonial() = Testimonial(
    image,
    fullName,
    function,
    review
)
