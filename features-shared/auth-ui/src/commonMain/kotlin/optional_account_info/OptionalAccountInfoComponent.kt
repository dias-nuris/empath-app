package optional_account_info

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import optional_account_info.model.Event
import optional_account_info.model.State

interface OptionalAccountInfoComponent {
    val screenStateFlow: StateFlow<State>
    fun onEvent(event: Event)
}

class DefaultOptionalAccountInfoComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, OptionalAccountInfoComponent {
    override val screenStateFlow = MutableStateFlow(State.CreateAccount())

    override fun onEvent(event: Event) = when (event) {
        is Event.FirstNameChanged -> onFirstNameChanged(event.name)
        is Event.LastNameChanged -> onLastNameChanged(event.name)
        is Event.GenderSelected -> onGenderSelected(event.gender)
        is Event.DateOfBirthSelected -> onDateOfBirthSelected(event.date)
        is Event.UserImageSelected -> onUserImageSelected(event.uri)
        is Event.SkipClicked -> onSkipClicked()
        is Event.SaveClicked -> onSaveClicked()
    }

    private fun onFirstNameChanged(firstName: String?) =
        screenStateFlow.update { state -> state.copy(firstName = firstName) }

    private fun onLastNameChanged(lastName: String?) =
        screenStateFlow.update { state -> state.copy(lastName = lastName) }

    private fun onGenderSelected(gender: String) =
        screenStateFlow.update { state -> state.copy(gender = gender) }

    private fun onDateOfBirthSelected(date: String) =
        screenStateFlow.update { state -> state.copy(dateOfBirth = date) }

    private fun onUserImageSelected(uri: String?) =
        screenStateFlow.update { state -> state.copy(image = uri) }

    private fun onSkipClicked() {
        TODO("Not yet implemented")
    }

    private fun onSaveClicked() {
        TODO("Not yet implemented")
    }
}