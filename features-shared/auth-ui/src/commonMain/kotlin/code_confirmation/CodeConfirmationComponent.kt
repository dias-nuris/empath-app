package code_confirmation

import com.arkivanov.decompose.ComponentContext
import dispatchers.AppDispatchers
import domain.model.AuthState
import domain.usecase.VerifyEmailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import code_confirmation.model.Event
import code_confirmation.model.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CodeConfirmationComponent {
    val screenStateFlow: StateFlow<State>
    fun onEvent(event: Event) = Unit
}

class DefaultCodeConfirmationComponent(
    componentContext: ComponentContext,
    private val state: AuthState,
    private val email: String,
    private val onBackChosen: () -> Unit,
    private val onRegistrationChosen: () -> Unit,
    private val onResetPasswordChosen: () -> Unit,
) : ComponentContext by componentContext, CodeConfirmationComponent, KoinComponent {
    private val dispatchers: AppDispatchers by inject()
    private val verifyEmailUseCase: VerifyEmailUseCase by inject()

    private val scope = CoroutineScope(dispatchers.main + SupervisorJob())
    override val screenStateFlow = MutableStateFlow(State.EntryCode(state))

    override fun onEvent(event: Event) = when (event) {
        is Event.BackClicked -> onBackChosen()
        is Event.CodeChanged -> onCodeChanged(event.code)
        is Event.RemoveCodeClicked -> onRemoveCodeClicked()
        is Event.CheckCodeClicked -> onCheckCodeClicked()
    }

    private fun onCodeChanged(code: String?) =
        screenStateFlow.update { state -> state.copy(code = code.orEmpty()) }

    private fun onRemoveCodeClicked() =
        screenStateFlow.update { state -> state.copy(code = state.code.dropLast(1)) }

    private fun onCheckCodeClicked() {
        scope.launch {
//            verifyEmailUseCase.invoke(email, screenStateFlow.value.code)
//                .onSuccess {
//                    when (state) {
//                        AuthState.REGISTRATION -> onRegistrationChosen()
//                        AuthState.RESET_PASSWORD -> onResetPasswordChosen()
//                    }
//                }.onError { code, _ ->
//                    when(code){
//            TODO(Handle error on verification email)
        }
    }
}
