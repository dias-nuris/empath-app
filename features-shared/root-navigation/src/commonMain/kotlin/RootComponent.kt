import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable

interface RootComponent: BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>

    fun onBackPressed()

    sealed interface Child{
        data class Auth(val component: AuthComponent): Child
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        handleBackButton = true,
        childFactory = ::child
    )

    override fun onBackPressed() = navigation.pop()

    private fun child(config: Config, childComponentContext: ComponentContext) =
        when (config) {
            Config.Auth -> authComponent(childComponentContext)
        }

    private fun authComponent(componentContext: ComponentContext) =
        RootComponent.Child.Auth(
            DefaultAuthComponent(
                componentContext = componentContext,
                onSuccessAuthentication = {
                    //todo
                }
            )
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config
    }
}