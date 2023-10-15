package be.christiano.portfolio.app.data.mapper

import be.christiano.portfolio.app.data.remote.dto.ServiceDto
import be.christiano.portfolio.app.domain.model.Service

fun ServiceDto.toService() = Service(
    id,
    image,
    title,
    description
)

//TODO: also the Entity?