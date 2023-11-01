package be.cbconnectit.portfolio.app.data.mapper

import be.cbconnectit.portfolio.app.data.local.entities.ServiceEntity
import be.cbconnectit.portfolio.app.data.remote.dto.ServiceDto
import be.cbconnectit.portfolio.app.domain.model.Service

fun ServiceEntity.toService() = Service(
    id,
    image,
    title,
    description
)

fun List<ServiceEntity>.toServices()= this.map { it.toService() }

fun ServiceDto.toService() = Service(
    id,
    image,
    title,
    description
)

fun ServiceDto.toServiceEntity() = ServiceEntity(
    id,
    image,
    title,
    description
)

fun List<ServiceDto>.toEntities() = this.map { it.toServiceEntity() }
@JvmName("dtoToServices")
fun List<ServiceDto>.toServices() = this.map { it.toService() }