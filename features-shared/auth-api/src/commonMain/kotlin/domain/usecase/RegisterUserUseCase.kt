package domain.usecase

import domain.model.CreateUser
import domain.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(query: CreateUser) = repository.registerUser(query)
}