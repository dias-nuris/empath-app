package domain.repository

import domain.model.*
import token.data.model.Token

interface AuthRepository {

    suspend fun registerUser(domain: CreateUser): Result<User>

    suspend fun updateUser(domain: User): Result<User>

    suspend fun loginUser(domain: Login): Result<Token>

    suspend fun resetPassword(domain: ResetPassword): Result<User>

    suspend fun sendEmailCode(email: String): Result<Any>

    suspend fun verifyEmail(domain: VerifyEmail): Result<Any>

    suspend fun getUser(): Result<User>

}