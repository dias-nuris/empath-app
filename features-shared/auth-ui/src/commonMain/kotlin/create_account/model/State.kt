package create_account.model

sealed class State{
    data object None: State()
    data object Loading: State()
    data class CreateAccount(
        val nickname: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
    ): State()
}