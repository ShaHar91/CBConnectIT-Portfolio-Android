package be.cbconnectit.portfolio.app.data.mapper

import be.cbconnectit.portfolio.app.data.local.entities.ServiceEntity
import be.cbconnectit.portfolio.app.data.remote.dto.ServiceDto
import be.cbconnectit.portfolio.app.domain.model.Service

fun ServiceEntity.toService() = Service(
    id = id,
    imageUrl = imageUrl,
    bannerImageUrl = bannerImageUrl,
    title = title,
    shortDescription = shortDescription,
    description = description,
    bannerDescription = bannerDescription,
    subServices = null, // TODO: add the correct relations
    extraInfo = extraInfo,
    tag = null, // TODO: add the correct relations
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<ServiceEntity>.toServices() = this.map { it.toService() }

fun ServiceDto.toService() = Service(
    id = id,
    imageUrl = imageUrl,
    bannerImageUrl = bannerImageUrl,
    title = title,
    shortDescription = shortDescription,
    description = description,
    bannerDescription = bannerDescription,
    subServices = subServices?.toServices(),
    extraInfo = extraInfo,
    tag = tag?.toTag(),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun ServiceDto.toServiceEntity(parentId: String?) = ServiceEntity(
    id = id,
    imageUrl = imageUrl,
    bannerImageUrl = bannerImageUrl,
    title = title,
    shortDescription = shortDescription,
    description = description,
    bannerDescription = bannerDescription,
    parentServiceId = parentId,
    extraInfo = extraInfo,
    tagId = tag?.id,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<ServiceDto>.toEntities() = this.flatMap { parentService ->
    listOfNotNull(parentService.toServiceEntity(null))
        .plus(parentService.subServices.orEmpty().map { it.toServiceEntity(parentService.id) })
}

@JvmName("dtoToServices")
fun List<ServiceDto>.toServices(): List<Service> = this.map { it.toService() }