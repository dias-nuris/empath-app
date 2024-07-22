package domain.usecase

import domain.model.VerifyEmail
import domain.repository.AuthRepository

class VerifyEmailUseCase(
    private val repository: AuthRepository,
) {
    suspend fun invoke(email: String, code: String) =
        repository.verifyEmail(VerifyEmail(email, code))
}