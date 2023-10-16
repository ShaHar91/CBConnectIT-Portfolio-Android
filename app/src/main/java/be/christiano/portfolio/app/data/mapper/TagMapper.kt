package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.local.entities.TagEntity
import be.christiano.portfolio.app.data.remote.dto.TagDto
import be.christiano.portfolio.app.domain.model.Tag

fun TagEntity.toTag() = Tag(
    id,
    name
)

fun List<TagEntity>.toTags() = this.map { it.toTag() }

fun TagDto.toTag() = Tag(
    id,
    name
)

fun TagDto.toTagEntity() = TagEntity(
    id,
    name
)

fun List<TagDto>.toEntities() = this.map { it.toTagEntity() }
@JvmName("dtoToTags")

fun List<TagDto>.toTags() = this.map { it.toTag() }