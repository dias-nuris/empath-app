import code_confirmation.CodeConfirmationComponent
import code_confirmation.DefaultCodeConfirmationComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import create_account.CreateAccountComponent
import create_account.DefaultCreateAccountComponent
import domain.model.AuthState
import email_verification.EmailVerificationComponent
import email_verification.DefaultEmailVerificationComponent
import kotlinx.serialization.Serializable
import login.DefaultLoginComponent
import login.LoginComponent
import optional_account_info.DefaultOptionalAccountInfoComponent
import optional_account_info.OptionalAccountInfoComponent
import password_recovery.DefaultPasswordRecoveryComponent
import password_recovery.PasswordRecoveryComponent
import privacy.DefaultPrivacyComponent
import privacy.PrivacyComponent

interface AuthComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    sealed interface Child {
        class Login(val component: LoginComponent) : Child
        class Privacy(val component: PrivacyComponent) : Child
        class CreateAccount(val component: CreateAccountComponent) : Child
        class CodeConfirmation(val component: CodeConfirmationComponent) : Child
        class EmailVerification(val component: EmailVerificationComponent) : Child
        class PasswordRecovery(val component: PasswordRecoveryComponent) : Child
        class OptionalAccountInfo(val component: OptionalAccountInfoComponent) : Child
    }
}


class DefaultAuthComponent(
    componentContext: ComponentContext,
    private val onSuccessAuthentication: () -> Unit,
) : ComponentContext by componentContext, AuthComponent {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Login,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createChild,
    )

    override fun onBackClicked() = navigation.pop()

    private fun createChild(
        config: Config,
        childComponentContext: ComponentContext,
    ): AuthComponent.Child = when (config) {
        is Config.Login -> loginComponent(childComponentContext)
        is Config.CreateAccount -> createAccountComponent(childComponentContext)
        is Config.Privacy -> privacyComponent(childComponentContext)
        is Config.CodeConfirmation -> codeConfirmationComponent(childComponentContext, config)
        is Config.EmailVerification -> emailVerificationComponent(childComponentContext, config)
        is Config.PasswordRecovery -> recoveryPasswordComponent(childComponentContext)
        is Config.OptionalAccountInfo -> optionalAccountInfoComponent(childComponentContext)
    }

    private fun loginComponent(componentContext: ComponentContext) =
        AuthComponent.Child.Login(
            DefaultLoginComponent(
                componentContext = componentContext,
                onResetPasswordChosen = {
                    navigation.push(Config.EmailVerification(AuthState.RESET_PASSWORD))
                },
                onRegistrationChosen = {
                    navigation.push(Config.EmailVerification(AuthState.REGISTRATION))
                },
                onPrivacyChosen = {
                    navigation.push(Config.Privacy)
                },
                onLoginSuccess = onSuccessAuthentication,
            )
        )

    private fun privacyComponent(
        componentContext: ComponentContext,
    ) = AuthComponent.Child.Privacy(
        DefaultPrivacyComponent(
            componentContext = componentContext,
            onBackChosen = ::onBackClicked,
        )
    )

    private fun createAccountComponent(
        componentContext: ComponentContext,
    ) = AuthComponent.Child.CreateAccount(
        DefaultCreateAccountComponent(
            componentContext = componentContext,
            onCreateAccountChosen = {
                navigation.push(Config.OptionalAccountInfo)
            },
            onBackChosen = {
                navigation.popWhile { topOfStack ->
                    topOfStack !is Config.EmailVerification
                }
            },
        )
    )

    private fun codeConfirmationComponent(
        componentContext: ComponentContext,
        config: Config.CodeConfirmation,
    ) = AuthComponent.Child.CodeConfirmation(
        DefaultCodeConfirmationComponent(
            componentContext = componentContext,
            state = config.authState,
            email = config.email,
            onBackChosen = ::onBackClicked,
            onRegistrationChosen = {
                navigation.push(Config.CreateAccount)
            },
            onResetPasswordChosen = {
                navigation.push(Config.PasswordRecovery)
            },
        )
    )

    private fun emailVerificationComponent(
        componentContext: ComponentContext,
        config: Config.EmailVerification,
    ) = AuthComponent.Child.EmailVerification(
        DefaultEmailVerificationComponent(
            componentContext = componentContext,
            state = config.authState,
            onBackChosen = ::onBackClicked,
            onSendEmailChosen = { email ->
                navigation.push(Config.CodeConfirmation(config.authState, email))
            },
            onResetPasswordChosen = {
                navigation.push(Config.PasswordRecovery)
            },
        )
    )

    private fun recoveryPasswordComponent(
        componentContext: ComponentContext,
    ) = AuthComponent.Child.PasswordRecovery(
        DefaultPasswordRecoveryComponent(
            componentContext = componentContext,
            onBackChosen = {
                navigation.popWhile { topOfStack ->
                    topOfStack !is Config.EmailVerification
                }
            },
        )
    )

    private fun optionalAccountInfoComponent(
        componentContext: ComponentContext,
    ) = AuthComponent.Child.OptionalAccountInfo(
        DefaultOptionalAccountInfoComponent(componentContext)
    )


    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config

        @Serializable
        data object Privacy : Config

        @Serializable
        data object CreateAccount : Config

        @Serializable
        data class CodeConfirmation(
            val authState: AuthState,
            val email: String,
        ) : Config

        @Serializable
        data class EmailVerification(
            val authState: AuthState,
        ) : Config

        @Serializable
        data object PasswordRecovery : Config

        @Serializable
        data object OptionalAccountInfo : Config
    }
}
