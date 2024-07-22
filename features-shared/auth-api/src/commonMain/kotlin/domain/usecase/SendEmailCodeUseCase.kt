package domain.usecase

import domain.repository.AuthRepository

class SendEmailCodeUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String) = repository.sendEmailCode(email)
}