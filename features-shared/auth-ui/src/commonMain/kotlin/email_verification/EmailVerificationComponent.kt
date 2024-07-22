package email_verification

import STRING_EMPTY
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import dialog.message.MessageDialogComponent
import dialog.message.MessageDialogConfig
import dialog.message.RealMessageDialogComponent
import dispatchers.AppDispatchers
import domain.model.AuthState
import domain.usecase.SendEmailCodeUseCase
import email_verification.model.Event
import email_verification.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface EmailVerificationComponent {
    val screenStateFlow: StateFlow<State>
    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>
    fun onEvent(event: Event)
}

class DefaultEmailVerificationComponent(
    componentContext: ComponentContext,
    state: AuthState,
    private val onBackChosen: () -> Unit,
    private val onSendEmailChosen: (String) -> Unit,
    private val onResetPasswordChosen: () -> Unit,
) : ComponentContext by componentContext, EmailVerificationComponent, KoinComponent {
    private val dispatchers: AppDispatchers by inject()
    private val sendEmailCodeUseCase: SendEmailCodeUseCase by inject()

    private val scope = CoroutineScope(dispatchers.main + SupervisorJob())
    override val screenStateFlow = MutableStateFlow(State.EntryEmail(state))

    private val messageDialogNavigation = SlotNavigation<MessageDialogConfig>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        serializer = MessageDialogConfig.serializer(),
        childFactory = ::createMessageDialog,
    )

    override fun onEvent(event: Event) = when (event) {
        is Event.EmailChanged -> onEmailChanged(event.email)
        is Event.SendCodeClicked -> onSendEmailClicked(event.state)
        is Event.ShowMessageDialog -> showMessageDialog(event.config)
        is Event.BackClicked -> onBackChosen()
    }

    private fun onEmailChanged(email: String?) =
        screenStateFlow.update { state -> state.copy(email = email.orEmpty()) }

    private fun showMessageDialog(config: MessageDialogConfig) =
        messageDialogNavigation.activate(config)

    private fun createMessageDialog(
        config: MessageDialogConfig,
        childComponentContext: ComponentContext
    ) = RealMessageDialogComponent(
        componentContext = childComponentContext,
        state = config,
        onAccessChosen = {
            when (config) {
                MessageDialogConfig.REGISTRATION_EMAIL_ALREADY_EXISTS -> {
                    onSendEmailClicked(AuthState.RESET_PASSWORD)
                    messageDialogNavigation.dismiss()
                }

                MessageDialogConfig.RESET_PASSWORD_EMAIL_NOT_REGISTERED -> {
                    onSendEmailClicked(AuthState.REGISTRATION)
                    messageDialogNavigation.dismiss()
                }

                else -> messageDialogNavigation.dismiss()
            }
        },
        onDismissChosen = {
            onEmailChanged(STRING_EMPTY)
            messageDialogNavigation.dismiss()
        }
    )

    private fun onSendEmailClicked(state: AuthState) {
        onSendEmailChosen(screenStateFlow.value.email)
        //TODO(Retrofit can't create adapter for object maybe library error)
//        scope.launch {
//            when (state) {
//                ToolbarState.REGISTRATION -> {
//                    sendEmailCodeUseCase.invoke(screenStateFlow.value.email)
//                        .onSuccess {
//                            onSendEmailChosen(screenStateFlow.value.email)
//                        }.onError { code, _ ->
//                            handleSendEmailError(state, code)
//                        }
//                }
//
//                ToolbarState.RESET_PASSWORD -> {
//                    //TODO(another request for sending code for reset password)
//                    onResetPasswordChosen()
//                }
//            }
//        }
    }

    private fun handleSendEmailError(state: AuthState, code: Int) {
        val config = when (state) {
            AuthState.REGISTRATION -> {
                when (code) {
                    409 -> MessageDialogConfig.REGISTRATION_EMAIL_ALREADY_EXISTS
                    422 -> MessageDialogConfig.TOO_MANY_VERIFICATION_ATTEMPTS
                    else -> MessageDialogConfig.UNKNOWN_ERROR
                }
            }

            AuthState.RESET_PASSWORD -> {
                when (code) {
                    409 -> MessageDialogConfig.RESET_PASSWORD_EMAIL_NOT_REGISTERED
                    422 -> MessageDialogConfig.TOO_MANY_VERIFICATION_ATTEMPTS
                    else -> MessageDialogConfig.UNKNOWN_ERROR
                }
            }
        }
        showMessageDialog(config)
    }

}