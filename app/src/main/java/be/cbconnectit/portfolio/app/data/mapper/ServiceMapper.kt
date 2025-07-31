package be.cbconnectit.portfolio.app.data.mapper

import be.cbconnectit.portfolio.app.data.local.entities.ServiceEntity
import be.cbconnectit.portfolio.app.data.remote.dto.ServiceDto
import be.cbconnectit.portfolio.app.domain.model.Service

fun ServiceEntity.toService() = Service(
    id,
    imageUrl,
    bannerImageUrl,
    title,
    shortDescription,
    description,
    bannerDescription,
    null, // TODO: add the correct relations
    extraInfo,
    null, // TODO: add the correct relations
    createdAt,
    updatedAt
)

fun List<ServiceEntity>.toServices() = this.map { it.toService() }

fun ServiceDto.toService() = Service(
    id,
    imageUrl,
    bannerImageUrl,
    title,
    shortDescription,
    description,
    bannerDescription,
    subServices?.toServices(),
    extraInfo,
    tag?.toTag(),
    createdAt,
    updatedAt
)

fun ServiceDto.toServiceEntity(parentId: String?) = ServiceEntity(
    id,
    imageUrl,
    bannerImageUrl,
    title,
    shortDescription,
    description,
    bannerDescription,
    parentId,
    extraInfo,
    tag?.id,
    createdAt,
    updatedAt
)

fun List<ServiceDto>.toEntities() = this.flatMap { parentService ->
    listOfNotNull(parentService.toServiceEntity(null))
        .plus(parentService.subServices.orEmpty().map { it.toServiceEntity(parentService.id) })
}

@JvmName("dtoToServices")
fun List<ServiceDto>.toServices(): List<Service> = this.map { it.toService() }