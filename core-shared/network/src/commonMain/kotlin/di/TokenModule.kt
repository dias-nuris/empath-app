package di

import com.example.project.BuildKonfig
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import logger.Logger
import network.provideHttpClient
import network.provideKtorHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import token.manager.TokenBaseSessionManager
import token.manager.TokenSessionManager
import token.data.TokenApi
import token.data.TokenRepositoryImpl
import token.data.createTokenApi
import token.domain.usecase.RefreshTokenUseCase
import token.domain.repo.TokenRepository
import token.domain.usecase.DeleteTokenUseCase
import token.domain.usecase.GetTokenUseCase
import token.domain.usecase.SaveTokenUseCase

private const val TOKEN_MODULE = "tokenModule"

val tokenModule = module {
    factory<HttpClient>(named(TOKEN_MODULE)) {
        provideHttpClient(
            json = get<Json>(),
            customLogger = get<Logger>(),
            sessionManager = null,
        )
    }
    factory<Ktorfit>(named(TOKEN_MODULE)) {
        provideKtorHttpClient(
            httpClient = get<HttpClient>(named(TOKEN_MODULE)),
            baseUrl = BuildKonfig.BASE_URL,
        )
    }
    factory { get<Ktorfit>(named(TOKEN_MODULE)).createTokenApi() }
    factory { RefreshTokenUseCase(repo = get()) }
    factory { DeleteTokenUseCase(prefs = get()) }
    factory { SaveTokenUseCase(prefs = get()) }
    factory { GetTokenUseCase(prefs = get()) }
    factory<TokenRepository> { TokenRepositoryImpl(api = get()) }
    single<TokenBaseSessionManager> {
        TokenSessionManager(
            appDispatchers = get(),
            logger = get(),
            saveTokenUseCase = get(),
            refreshTokenUseCase = get(),
            getTokenUseCase = get(),
            deleteTokenUseCase = get(),
        )
    }
}