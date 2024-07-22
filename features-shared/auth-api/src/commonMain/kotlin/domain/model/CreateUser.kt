package domain.model

data class CreateUser(
    val nickname: String,
    val email: String,
    val password: String,
)
