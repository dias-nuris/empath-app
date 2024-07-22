package email_verification.model

import dialog.message.MessageDialogConfig
import domain.model.AuthState

sealed class Event {
    data class EmailChanged(val email: String?) : Event()
    data class SendCodeClicked(val state: AuthState) : Event()
    data class ShowMessageDialog(val config: MessageDialogConfig) : Event()
    data object BackClicked : Event()
}
