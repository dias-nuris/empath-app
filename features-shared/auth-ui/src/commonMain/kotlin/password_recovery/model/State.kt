package password_recovery.model

sealed class State{
    data object None: State()
    data object Loading: State()
    data class PasswordRecovery(
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
    ): State()
}