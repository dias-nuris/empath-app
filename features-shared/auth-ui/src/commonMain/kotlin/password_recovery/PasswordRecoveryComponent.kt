package password_recovery

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import password_recovery.model.Event
import password_recovery.model.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface PasswordRecoveryComponent: BackHandlerOwner {
    val screenStateFlow: StateFlow<State>
    fun onEvent(event: Event)
}

class DefaultPasswordRecoveryComponent(
    componentContext: ComponentContext,
    private val onBackChosen: () -> Unit,
) : ComponentContext by componentContext, PasswordRecoveryComponent, KoinComponent {
    private val dispatchers: AppDispatchers by inject()

    private val scope = CoroutineScope(dispatchers.main + SupervisorJob())
    override val screenStateFlow = MutableStateFlow(State.PasswordRecovery())

    override fun onEvent(event: Event) = when (event) {
        is Event.BackClicked -> onBackChosen()
        is Event.PasswordChanged -> onPasswordChanged(event.password)
        is Event.RepeatedPasswordChanged -> onRepeatedPasswordChanged(event.password)
        is Event.ShowPassword -> onPasswordShowClicked()
        is Event.ShowRepeatedPassword -> onRepeatedPasswordShowClicked()
        is Event.PasswordRecoveryClicked -> onResetPasswordClicked()
    }

    private fun onPasswordChanged(password: String?) =
        screenStateFlow.update { state -> state.copy(password = password.orEmpty()) }

    private fun onRepeatedPasswordChanged(password: String?) =
        screenStateFlow.update { state -> state.copy(repeatedPassword = password.orEmpty()) }

    private fun onPasswordShowClicked() =
        screenStateFlow.update { state -> state.copy(isPasswordVisible = !state.isPasswordVisible) }

    private fun onRepeatedPasswordShowClicked() = screenStateFlow.update { state ->
        state.copy(isRepeatedPasswordVisible = !state.isRepeatedPasswordVisible)
    }

    private fun onResetPasswordClicked() {
        scope.launch {
            //request to reset password todo
        }
    }
}