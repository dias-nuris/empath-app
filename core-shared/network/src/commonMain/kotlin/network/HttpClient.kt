package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import token.manager.TokenBaseSessionManager
import token.authorizationInterceptor

internal fun provideJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    explicitNulls = false
    encodeDefaults = true
    prettyPrint = true
}


internal fun provideHttpClient(
    json: Json,
    customLogger: logger.Logger,
    sessionManager: TokenBaseSessionManager? = null,
) = HttpClient {

    install(HttpRedirect) {
        checkHttpMethod = false
        allowHttpsDowngrade = false
    }

    install(DefaultRequest) {
        header(HttpHeaders.AcceptLanguage, "ru")
        header("Locale", "ru")
    }
    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }

    install(ContentNegotiation) {
        json(json)
    }
    if (sessionManager != null) {
        authorizationInterceptor(
            logger = customLogger,
            sessionManager = sessionManager,
        )
    }
    HttpResponseValidator {
        handleResponseExceptionWithRequest { cause, request ->
            customLogger.d(KTOR_CLIENT, "Error url: ${request.url}, method: ${request.method}")
            customLogger.d(KTOR_CLIENT, cause.message.orEmpty())
        }
    }

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                customLogger.d(KTOR_CLIENT, message)
            }
        }
        level = LogLevel.ALL
    }

}
