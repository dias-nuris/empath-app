package domain.usecase

import domain.model.Login
import domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.loginUser(Login(email, password))
}