package token.domain.repo

import token.data.model.Token
import token.data.model.TokenEntity

interface TokenRepository {

    suspend fun refreshToken(token: Token): Token

}