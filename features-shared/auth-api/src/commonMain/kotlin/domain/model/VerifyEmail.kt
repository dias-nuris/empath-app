package domain.model

data class VerifyEmail(
    val email: String,
    val code: String,
)