package be.christiano.portfolio.app.data.remote.api

import be.christiano.portfolio.app.data.remote.RestApiBuilder
import be.christiano.portfolio.app.data.remote.dto.TestimonialDto
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.DefaultJson

class TestimonialApiImpl(
    private val client: RestApiBuilder
) : TestimonialApi {
    override suspend fun fetchAllTestimonials(): List<TestimonialDto> {
        val call = client.api.get("/ShaHar91/Portfolio/master/public/testimonials.json")
        val works = call.bodyAsText()

        return DefaultJson.decodeFromString(works)
    }
}

interface TestimonialApi {
    suspend fun fetchAllTestimonials(): List<TestimonialDto>
}