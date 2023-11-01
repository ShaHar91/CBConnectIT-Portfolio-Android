package be.cbconnectit.portfolio.app.data.mapper

import be.cbconnectit.portfolio.app.data.local.entities.ExperienceEntity
import be.cbconnectit.portfolio.app.data.remote.dto.ExperienceDto
import be.cbconnectit.portfolio.app.domain.model.Experience

fun ExperienceEntity.toExperience() = Experience(
    id,
    jobPosition,
    shortDescription,
    description,
    company,
    from,
    to,
    techStacks
)

fun List<ExperienceEntity>.toExperiences() = this.map { it.toExperience() }

fun ExperienceDto.toExperience() = Experience(
    id,
    jobPosition,
    shortDescription,
    description,
    company,
    from,
    to,
    techStacks
)

fun ExperienceDto.toExperienceEntity() = ExperienceEntity(
    id,
    jobPosition,
    shortDescription,
    description,
    company,
    from,
    to,
    techStacks
)

fun List<ExperienceDto>.toEntities() = this.map { it.toExperienceEntity() }
@JvmName("dtoToExperiences")
fun List<ExperienceDto>.toExperiences() = this.map { it.toExperience() }