package be.christiano.portfolio.app.data.remote.api

import be.christiano.portfolio.app.data.remote.RestApiBuilder
import be.christiano.portfolio.app.data.remote.dto.ServiceDto
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.DefaultJson

class ServiceApiImpl(
    private val client: RestApiBuilder
): ServiceApi {
    override suspend fun fetchAllServices(): List<ServiceDto> {
        val call = client.api.get("/ShaHar91/Portfolio/master/public/services.json")
        val services = call.bodyAsText()

        return DefaultJson.decodeFromString(services)
    }
}

interface ServiceApi {
    suspend fun fetchAllServices(): List<ServiceDto>
}