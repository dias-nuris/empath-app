package network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import de.jensklingenberg.ktorfit.ktorfitBuilder
import io.ktor.client.HttpClient

internal const val KTOR_CLIENT = "KtorClient"
internal const val TIME_OUT = 30_000L


internal fun provideKtorHttpClient(
    httpClient: HttpClient,
    baseUrl: String,
): Ktorfit {

    return ktorfit {
        baseUrl(baseUrl)
        httpClient(httpClient)
    }
}