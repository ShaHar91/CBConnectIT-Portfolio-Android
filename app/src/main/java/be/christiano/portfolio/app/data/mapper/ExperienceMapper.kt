package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.remote.dto.ExperienceDto
import be.christiano.portfolio.app.domain.model.Experience

fun ExperienceDto.toExperience() = Experience(
    jobPosition, description, company, from, to
)

//TODO: also the Entity?