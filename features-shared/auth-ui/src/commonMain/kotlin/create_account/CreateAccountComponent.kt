package create_account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import create_account.model.Event
import create_account.model.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CreateAccountComponent: BackHandlerOwner {
    val screenStateFlow: StateFlow<State>
    fun onEvent(event: Event) = Unit
}

class DefaultCreateAccountComponent(
    componentContext: ComponentContext,
    private val onBackChosen: () -> Unit,
    private val onCreateAccountChosen: () -> Unit,
) : ComponentContext by componentContext, CreateAccountComponent, KoinComponent {
    private val dispatchers: AppDispatchers by inject()

    private val scope = CoroutineScope(dispatchers.main + SupervisorJob())
    override val screenStateFlow = MutableStateFlow(State.CreateAccount())

    override fun onEvent(event: Event) = when(event){
        is Event.BackClicked -> onBackChosen()
        is Event.NicknameChanged -> onNicknameChanged(event.nickname)
        is Event.PasswordChanged -> onPasswordChanged(event.password)
        is Event.RepeatedPasswordChanged -> onRepeatedPasswordChanged(event.password)
        is Event.ShowPasswordClicked -> onPasswordShowClicked()
        is Event.ShowRepeatedPasswordClicked -> onRepeatedPasswordShowClicked()
        is Event.CreateAccountClicked -> onCreateAccountClicked()
    }

    private fun onNicknameChanged(nickname: String?) =
        screenStateFlow.update { state -> state.copy(nickname = nickname.orEmpty()) }

    private fun onPasswordChanged(password: String?) =
        screenStateFlow.update { state -> state.copy(password = password.orEmpty()) }

    private fun onRepeatedPasswordChanged(password: String?) =
        screenStateFlow.update { state -> state.copy(repeatedPassword = password.orEmpty()) }

    private fun onPasswordShowClicked() =
        screenStateFlow.update { state -> state.copy(isPasswordVisible = !state.isPasswordVisible) }

    private fun onRepeatedPasswordShowClicked() =
        screenStateFlow.update { state ->
            state.copy(isRepeatedPasswordVisible = !state.isRepeatedPasswordVisible)
        }

    private fun onCreateAccountClicked() {
        scope.launch {
            //TODO request to creating account
            onCreateAccountChosen()
        }
    }
}