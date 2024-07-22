package optional_account_info.model

sealed class Event {
    data class FirstNameChanged(val name: String?) : Event()
    data class LastNameChanged(val name: String?) : Event()
    data class GenderSelected(val gender: String) : Event()
    data class DateOfBirthSelected(val date: String) : Event()
    data class UserImageSelected(val uri: String?) : Event()
    data object SkipClicked : Event()
    data object SaveClicked : Event()
}
