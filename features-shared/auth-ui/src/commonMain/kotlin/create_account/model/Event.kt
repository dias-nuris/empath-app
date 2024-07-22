package create_account.model

sealed class Event{
    data class NicknameChanged(val nickname: String?): Event()
    data class PasswordChanged(val password: String?): Event()
    data class RepeatedPasswordChanged(val password: String?): Event()
    data object ShowPasswordClicked: Event()
    data object ShowRepeatedPasswordClicked: Event()
    data object CreateAccountClicked: Event()
    data object BackClicked: Event()
}

