package token.manager

import dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logger.Logger
import org.koin.core.component.KoinComponent
import token.data.model.Token
import token.domain.usecase.DeleteTokenUseCase
import token.domain.usecase.GetTokenUseCase
import token.domain.usecase.RefreshTokenUseCase
import token.domain.usecase.SaveTokenUseCase

const val TOKEN_SESSION_MANAGER = "TokenSessionManager"

class TokenSessionManager(
    appDispatchers: AppDispatchers,
    private val logger: Logger,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
) : TokenBaseSessionManager, KoinComponent {

    private val scope = CoroutineScope(appDispatchers.io)

    private val _tokenState = MutableStateFlow<Token?>(null)
    override val tokenState: StateFlow<Token?> = _tokenState.asStateFlow()

    init {
        listenToken()
    }

    override fun listenToken() {
        scope.launch {
            getTokenUseCase.invoke().collectLatest { currentToken ->
                logger.d(TOKEN_SESSION_MANAGER, "listenToken: $currentToken")
                _tokenState.update { currentToken }
            }
        }
    }

    override suspend fun getToken(): Token = getTokenUseCase.invoke().first()

    override suspend fun deleteToken() = deleteTokenUseCase.invoke()

    override suspend fun saveToken(token: Token) = saveTokenUseCase.invoke(token)

    override suspend fun refreshToken(): Token {
        val currentToken = getToken()
        val newTokens = refreshTokenUseCase.invoke(currentToken)
        logger.d(TOKEN_SESSION_MANAGER, "refreshToken: $newTokens")
        saveTokenUseCase.invoke(newTokens)
        return newTokens
    }

}
