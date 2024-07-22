package domain.model

data class ResetPassword(
    val email: String,
    val password: String,
    val isVisible: Boolean = false,
)