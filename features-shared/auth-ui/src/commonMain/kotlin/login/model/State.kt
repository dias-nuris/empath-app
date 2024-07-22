sealed class State {
    data object None : State()
    data object Loading : State()
    data class Login(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
    ) : State()
}
