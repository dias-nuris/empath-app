package privacy

import com.arkivanov.decompose.ComponentContext
import privacy.model.Event

interface PrivacyComponent {
    fun onEvent(event: Event)
}

class DefaultPrivacyComponent(
    componentContext: ComponentContext,
    private val onBackChosen: () -> Unit,
): ComponentContext by componentContext, PrivacyComponent {

    override fun onEvent(event: Event) {
        when(event){
            is Event.BackClicked -> onBackChosen()
        }
    }

}