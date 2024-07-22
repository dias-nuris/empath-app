package token.domain.usecase

import token.data.model.Token
import token.domain.repo.TokenRepository

class RefreshTokenUseCase(
    private val repo: TokenRepository
) {
    suspend fun invoke(token: Token) = repo.refreshToken(token)
}