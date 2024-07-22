package login.model

import dialog.message.MessageDialogConfig

sealed class Event {
    data class EmailChanged(val email: String?) : Event()
    data class PasswordChanged(val password: String?) : Event()

    data object LoginClicked: Event()
    data object PasswordShowClicked: Event()
    data object PrivacyClicked: Event()
    data object ResetPasswordClicked: Event()
    data object RegistrationClicked: Event()
    data object GoogleAuthClicked: Event()
    data object FacebookAuthClicked: Event()

    data class ShowMessageDialog(val config: MessageDialogConfig): Event()
    data object ShowPasswordTipsDialog: Event()

}
