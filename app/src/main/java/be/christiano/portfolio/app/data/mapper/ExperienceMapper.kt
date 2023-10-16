package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.local.entities.ExperienceEntity
import be.christiano.portfolio.app.data.remote.dto.ExperienceDto
import be.christiano.portfolio.app.domain.model.Experience

fun ExperienceEntity.toExperience() = Experience(
    id,
    jobPosition,
    description,
    company,
    from,
    to
)

fun List<ExperienceEntity>.toExperiences() = this.map { it.toExperience() }

fun ExperienceDto.toExperience() = Experience(
    id,
    jobPosition,
    description,
    company,
    from,
    to
)

fun ExperienceDto.toExperienceEntity() = ExperienceEntity(
    id,
    jobPosition,
    description,
    company,
    from,
    to
)

fun List<ExperienceDto>.toEntities() = this.map { it.toExperienceEntity() }
@JvmName("dtoToExperiences")
fun List<ExperienceDto>.toExperiences() = this.map { it.toExperience() }