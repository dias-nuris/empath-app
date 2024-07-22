package token.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import token.data.model.Token

interface TokenBaseSessionManager {

    val tokenState: StateFlow<Token?>

    fun listenToken()

    suspend fun getToken(): Token

    suspend fun deleteToken(): Boolean

    suspend fun saveToken(token: Token)

    suspend fun refreshToken(): Token

}