package token

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import logger.Logger
import token.manager.TokenBaseSessionManager

const val AUTHORIZATION_INTERCEPTOR = "AuthorizationInterceptor"

fun HttpClientConfig<*>.authorizationInterceptor(
    logger: Logger,
    sessionManager: TokenBaseSessionManager,
) {
    install(Auth) {
        bearer {
            loadTokens {
                val tokens = sessionManager.getToken()
                logger.d(AUTHORIZATION_INTERCEPTOR, "loadTokens: $tokens")
                BearerTokens(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken,
                )
            }
            refreshTokens {
                val newTokens = sessionManager.refreshToken()
                logger.d(AUTHORIZATION_INTERCEPTOR, "refreshTokens: $newTokens")
                BearerTokens(
                    accessToken = newTokens.accessToken,
                    refreshToken = newTokens.refreshToken,
                )
            }
        }
    }
}
