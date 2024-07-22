package code_confirmation.model

sealed class Event {
    data class CodeChanged(val code: String?) : Event()
    data object RemoveCodeClicked : Event()
    data object CheckCodeClicked : Event()
    data object BackClicked: Event()
}