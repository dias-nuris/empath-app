package password_recovery.model

sealed class Event{
    data class PasswordChanged(val password: String?): Event()
    data class RepeatedPasswordChanged(val password: String?): Event()

    data object ShowPassword: Event()
    data object ShowRepeatedPassword: Event()

    data object PasswordRecoveryClicked: Event()

    data object BackClicked: Event()
}
