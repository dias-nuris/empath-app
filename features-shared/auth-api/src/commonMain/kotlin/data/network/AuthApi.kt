package data.network

import de.jensklingenberg.ktorfit.http.*
import io.ktor.http.content.PartData
import data.model.*
import token.data.model.TokenEntity

interface AuthApi {
    @POST("/api_v1/auth/users")
    fun registerUser(@Body body: CreateUserRequest): Result<UserRequest>

    @PATCH("/api_v1/auth/users")
    fun updateUser(@Body body: UserRequest): Result<UserRequest>

    @Multipart
    @POST("/api_v1/auth/auth/login")
    suspend fun loginUser(@Part part: List<PartData>): Result<TokenEntity>

    @PATCH("/api_v1/auth/users/reset_password")
    fun resetPassword(@Body body: ResetPasswordRequest): Result<UserRequest>

    @POST("/api_v1/auth/email")
    fun sendEmailCode(@Query("email_in") email: String): Result<Any>

    @POST("/api_v1/auth/email/verify")
    fun verifyEmail(@Body body: VerifyEmailRequest): Result<Any>

    @GET("/api_v1/auth/users/email/me")
    fun getUser(): Result<UserRequest>

}