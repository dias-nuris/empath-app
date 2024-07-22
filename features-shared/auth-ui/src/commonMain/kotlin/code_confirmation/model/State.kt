package code_confirmation.model

import domain.model.AuthState

sealed class State {
    data object None : State()
    data object Loading : State()
    data class EntryCode(
        val state: AuthState,
        val code: String = "",
    ) : State()
}