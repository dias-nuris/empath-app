package email_verification.model

import domain.model.AuthState

sealed class State {
    data object None : State()
    data object Loading : State()
    data class EntryEmail(
        val authState: AuthState,
        val email: String = "",
    ) : State()
}