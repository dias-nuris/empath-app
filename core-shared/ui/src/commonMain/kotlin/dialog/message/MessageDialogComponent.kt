package dialog.message

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface MessageDialogComponent {
    val screenStateFlow: StateFlow<MessageDialogConfig>
    fun onEvent(event: Event)

    sealed class Event{
        data object DismissClicked: Event()
        data object AccessClicked: Event()
    }
}

class RealMessageDialogComponent(
    state: MessageDialogConfig,
    private val onAccessChosen: () -> Unit,
    private val onDismissChosen: () -> Unit,
    componentContext: ComponentContext,
) : ComponentContext by componentContext, MessageDialogComponent {
    override val screenStateFlow = MutableStateFlow(state)

    override fun onEvent(event: MessageDialogComponent.Event) = when (event) {
        is MessageDialogComponent.Event.DismissClicked -> onDismissChosen()
        is MessageDialogComponent.Event.AccessClicked -> onAccessChosen()
    }

}

class FakeMessageDialogComponent : MessageDialogComponent {
    override val screenStateFlow = MutableStateFlow(MessageDialogConfig.UNKNOWN_ERROR)
    override fun onEvent(event: MessageDialogComponent.Event) = Unit
}

