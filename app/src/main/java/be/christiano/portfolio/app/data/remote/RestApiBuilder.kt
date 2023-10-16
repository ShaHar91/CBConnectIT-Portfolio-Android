package be.christiano.portfolio.app.data.remote

import be.christiano.portfolio.app.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class RestApiBuilder {

    val api = HttpClient(Android) {
        install(Logging) {
            level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        }

        expectSuccess = true

        install(ContentNegotiation){ json() }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                throw Exception("Some error")
            }
        }

        defaultRequest {
            url("https://raw.githubusercontent.com")
            contentType(ContentType.Application.Json)
        }
    }
}
//
//sealed class Result<T>(val data: T? = null, val message: String? = null) {
//    class Success<T>(data: T?): Result<T>(data)
//    class Error<T>(message: String): Result<T>(message = message)
//}
