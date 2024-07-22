package token.data

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import token.data.model.TokenEntity

interface TokenApi {
    @POST("users/refresh")
    suspend fun refreshToken(@Body tokenEntity: TokenEntity): TokenEntity
}