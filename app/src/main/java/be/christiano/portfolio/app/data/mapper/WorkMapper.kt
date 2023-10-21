package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.local.entities.WorkEntity
import be.christiano.portfolio.app.data.remote.dto.WorkDto
import be.christiano.portfolio.app.domain.model.Link
import be.christiano.portfolio.app.domain.model.Tag
import be.christiano.portfolio.app.domain.model.Work

fun WorkEntity.toWork(links: List<Link> = emptyList(), tags: List<Tag> = emptyList()) = Work(
    id,
    bannerImage,
    image,
    title,
    shortDescription,
    description,
    links,
    tags
)

fun List<WorkEntity>.toWorks() = this.map { it.toWork() }

fun WorkDto.toWork() = Work(
    id,
    bannerImage,
    image,
    title,
    shortDescription,
    description,
    links.toLinks(),
    tags.toTags()
)

fun WorkDto.toWorkEntity() = WorkEntity(
    id,
    bannerImage,
    image,
    title,
    shortDescription,
    description,
)

fun List<WorkDto>.toEntities() = this.map { it.toWorkEntity() }

@JvmName("dtoToWorks")
fun List<WorkDto>.toWorks() = this.map { it.toWork() }

