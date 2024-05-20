package com.example.mobilechallenge.common.ui.state

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface ViewStateDelegate<UiState, Event> {

    val uiState: Flow<UiState> // Declarative description of the UI based on the current state.
    val stateValue: UiState // State is read only, must be changed through actions

    val event: Flow<Event>

    suspend fun ViewStateDelegate<UiState, Event>.reduce(action: (state: UiState) -> UiState) // Take current state and with an action, set a new state
    fun CoroutineScope.asyncReduce(action: (state: UiState) -> UiState)
    suspend fun ViewStateDelegate<UiState, Event>.sendEvent(event: Event)
}

class ViewStateDelegateImpl<UiState, Event>(
    initialUiState: UiState,
    eventChannelCapacity: Int = Channel.BUFFERED
) : ViewStateDelegate<UiState, Event> {

    private val stateFlow = MutableStateFlow(initialUiState) // Ui State source of truth of the app

    override val uiState: Flow<UiState>
        get() = stateFlow.asStateFlow()

    override val stateValue: UiState
        get() = stateFlow.value

    private val eventChannel = Channel<Event>(eventChannelCapacity)

    override val event: Flow<Event>
        get() = eventChannel.receiveAsFlow()

    private val mutex = Mutex()

    override suspend fun ViewStateDelegate<UiState, Event>.reduce(action: (state: UiState) -> UiState) {
        mutex.withLock { stateFlow.emit(action(stateValue)) }
    }

    override fun CoroutineScope.asyncReduce(action: (state: UiState) -> UiState) {
        launch { reduce(action) }
    }

    override suspend fun ViewStateDelegate<UiState, Event>.sendEvent(event: Event) {
        eventChannel.send(event)
    }

}

@Composable
fun <R> ViewStateDelegate<R, *>.collectWithLifecycle(
    minActiveState: Lifecycle.State = androidx.lifecycle.Lifecycle.State.STARTED
) = this.uiState.collectAsStateWithLifecycle(
    initialValue = this.stateValue,
    minActiveState = minActiveState
)

fun <State, Event> ViewStateDelegate<State, Event>.collectEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    event.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect { event ->
        block.invoke(event)
    }
}