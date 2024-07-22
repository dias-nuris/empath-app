package optional_account_info.model


sealed class State {
    data object None: State()
    data object Loading: State()
    data class CreateAccount(
        val image: String? = null,
        val firstName: String? = null,
        val lastName: String? = null,
        val dateOfBirth: String? = null,
        val gender: String? = null,
    ): State()
}