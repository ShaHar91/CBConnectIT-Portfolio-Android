package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.local.entities.WorkEntity
import be.christiano.portfolio.app.data.remote.dto.WorkDto
import be.christiano.portfolio.app.domain.model.Tag
import be.christiano.portfolio.app.domain.model.Work

fun WorkEntity.toWork(tags: List<Tag> = emptyList()) = Work(
    id,
    image,
    title,
    description,
    link,
    tags
)

fun List<WorkEntity>.toWorks() = this.map { it.toWork() }

fun WorkDto.toWork() = Work(
    id,
    image,
    title,
    description,
    link,
    tags.toTags()
)

fun WorkDto.toWorkEntity() = WorkEntity(
    id,
    image,
    title,
    description,
    link
)

fun List<WorkDto>.toEntities() = this.map { it.toWorkEntity() }
@JvmName("dtoToWorks")
fun List<WorkDto>.toWorks() = this.map { it.toWork() }

