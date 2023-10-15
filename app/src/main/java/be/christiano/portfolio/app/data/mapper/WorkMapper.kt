package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.remote.dto.TagDto
import be.christiano.portfolio.app.data.remote.dto.WorkDto
import be.christiano.portfolio.app.domain.model.Tag
import be.christiano.portfolio.app.domain.model.Work

fun WorkDto.toWork() = Work(
    image,
    title,
    description,
    link,
    tags.map { it.toTag() }
)

fun TagDto.toTag() = Tag(
    name
)