package data.repository

import io.ktor.client.request.forms.formData
import data.mapper.*
import data.network.AuthApi
import domain.model.*
import domain.repository.AuthRepository
import token.data.mapper.toDomain
import token.data.model.Token

class AuthRepositoryImpl(
    private val api: AuthApi,
) : AuthRepository {

    override suspend fun registerUser(domain: CreateUser): Result<User> {
        val request = api.registerUser(domain.toData())
        return request.map { it.toDomain() }
    }

    override suspend fun updateUser(domain: User): Result<User> {
        val request = api.updateUser(domain.toData())
        return request.map { it.toDomain() }
    }

    override suspend fun loginUser(domain: Login): Result<Token> {
        val multipart = formData {
            append("email", domain.email)
            append("password", domain.password)
        }
        val request = api.loginUser(multipart)
        return request.map { it.toDomain() }
    }

    override suspend fun resetPassword(domain: ResetPassword): Result<User> {
        val request = api.resetPassword(domain.toData())
        return request.map { it.toDomain() }
    }

    override suspend fun sendEmailCode(email: String): Result<Any> {
        return api.sendEmailCode(email)
    }

    override suspend fun verifyEmail(domain: VerifyEmail): Result<Any> {
        return api.verifyEmail(domain.toData())
    }

    override suspend fun getUser(): Result<User> {
        val request = api.getUser()
        return request.map { it.toDomain() }
    }

}