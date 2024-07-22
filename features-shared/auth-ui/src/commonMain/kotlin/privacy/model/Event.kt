package privacy.model

sealed class Event {
    data object BackClicked: Event()
}