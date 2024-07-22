package login

import State
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
import dialog.password_tips.PasswordTipsComponent
import dialog.password_tips.PasswordTipsDialogConfig
import dialog.password_tips.RealPasswordTipsComponent
import dispatchers.AppDispatchers
import domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import login.model.Event
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface LoginComponent {
    val screenStateFlow: StateFlow<State>
    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>
    val passwordTipsDialog: Value<ChildSlot<*, PasswordTipsComponent>>
    fun onEvent(event: Event)
}

class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val onRegistrationChosen: () -> Unit,
    private val onPrivacyChosen: () -> Unit,
    private val onResetPasswordChosen: () -> Unit,
    private val onLoginSuccess: () -> Unit,
) : ComponentContext by componentContext, LoginComponent, KoinComponent {
    private val dispatchers: AppDispatchers by inject()
    private val loginUseCase: LoginUseCase by inject()

    private val scope = CoroutineScope(dispatchers.main + SupervisorJob())
    override val screenStateFlow = MutableStateFlow(State.Login())

    private val messageDialogNavigation = SlotNavigation<MessageDialogConfig>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = "message",
        serializer = MessageDialogConfig.serializer(),
        childFactory = ::createMessageDialog,
    )

    private val passwordTipsNavigation = SlotNavigation<PasswordTipsDialogConfig>()
    override val passwordTipsDialog: Value<ChildSlot<*, PasswordTipsComponent>> = childSlot(
        source = passwordTipsNavigation,
        key = "passwordTips",
        serializer = PasswordTipsDialogConfig.serializer(),
        childFactory = ::createPasswordTipsDialog,
    )

    override fun onEvent(event: Event) = when (event) {
        is Event.ShowPasswordTipsDialog -> showPasswordTipsDialog()
        is Event.ShowMessageDialog -> showMessageDialog(event.config)
        is Event.EmailChanged -> onEmailChanged(event.email)
        is Event.PasswordChanged -> onPasswordChanged(event.password)
        is Event.PasswordShowClicked -> onPasswordShowClicked()
        is Event.PrivacyClicked -> onPrivacyClicked()
        is Event.ResetPasswordClicked -> onResetPasswordClicked()
        is Event.RegistrationClicked -> onRegistrationClicked()
        is Event.GoogleAuthClicked -> onGoogleAuthClicked()
        is Event.FacebookAuthClicked -> onFacebookAuthClicked()
        is Event.LoginClicked -> onLoginClicked()
    }

    private fun showPasswordTipsDialog() =
        passwordTipsNavigation.activate(PasswordTipsDialogConfig.UNKNOWN_ERROR)

    private fun showMessageDialog(config: MessageDialogConfig) =
        messageDialogNavigation.activate(config)

    private fun onEmailChanged(email: String?) =
        screenStateFlow.update { state -> state.copy(email = email.orEmpty()) }

    private fun onPasswordChanged(password: String?) =
        screenStateFlow.update { state -> state.copy(password = password.orEmpty()) }

    private fun onPasswordShowClicked() =
        screenStateFlow.update { state -> state.copy(isPasswordVisible = !state.isPasswordVisible) }

    private fun onPrivacyClicked() = onPrivacyChosen()
    private fun onResetPasswordClicked() = onResetPasswordChosen()
    private fun onRegistrationClicked() = onRegistrationChosen()

    private fun onGoogleAuthClicked() {
//        TODO("Need to be implemented with Google api in the future")
    }

    private fun onFacebookAuthClicked() {
//        TODO("Need to be implemented with Facebook api in the future")
    }

    private fun createMessageDialog(
        config: MessageDialogConfig,
        childComponentContext: ComponentContext,
    ) = RealMessageDialogComponent(
        componentContext = childComponentContext,
        state = config,
        onAccessChosen = messageDialogNavigation::dismiss,
        onDismissChosen = messageDialogNavigation::dismiss,
    )

    private fun createPasswordTipsDialog(
        config: PasswordTipsDialogConfig,
        childComponentContext: ComponentContext,
    ) = RealPasswordTipsComponent(
        componentContext = childComponentContext,
        onDismissChosen = passwordTipsNavigation::dismiss,
    )

    private fun onLoginClicked() {
        val state = screenStateFlow.value
        scope.launch {
            if (!PasswordRegex.matches(state.password)) {
                showPasswordTipsDialog()
                return@launch
            }
//            loginUseCase(
//                email = state.email,
//                password = state.password
//            ).onError { code, _ ->
//                when (code) {
//                    422 -> showPasswordTipsDialog()
//                    429 -> showMessageDialog(MessageDialogConfig.TOO_MANY_LOGIN_ATTEMPTS)
//                    else -> showMessageDialog(MessageDialogConfig.UNKNOWN_ERROR)
//                }
//            }.onSuccess { token ->
//                saveTokenUseCase(token)
//                onLoginSuccess()
//            }
            onLoginSuccess()
        }
    }


    companion object {
        private val PasswordRegex =
            Regex("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,128}$")
    }
}