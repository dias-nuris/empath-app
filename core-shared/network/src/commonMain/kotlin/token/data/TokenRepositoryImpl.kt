package token.data

import token.data.mapper.toData
import token.data.mapper.toDomain
import token.data.model.Token
import token.domain.repo.TokenRepository

class TokenRepositoryImpl(
    private val api: TokenApi,
) : TokenRepository {
    override suspend fun refreshToken(token: Token): Token =
        api.refreshToken(token.toData()).toDomain()
}