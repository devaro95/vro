package com.vro.fragment

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.event.VROSingleLiveEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.navstarter.VRONavStarter
import com.vro.state.VRODialogState
import com.vro.state.VROState
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.Serializable

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialViewState: S

    private lateinit var viewState: S

    private val observableState: MutableSharedFlow<S> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state: Flow<S> = observableState

    internal val dialogState: VROSingleLiveEvent<VRODialogState> = VROSingleLiveEvent()

    internal val errorState: VROSingleLiveEvent<Throwable> = VROSingleLiveEvent()

    internal val navigationState: VROSingleLiveEvent<VRONavigationState<D>> = VROSingleLiveEvent()

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    internal fun createInitialState() {
        if (!this::viewState.isInitialized) {
            viewState = initialViewState
        }
    }

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    internal fun setInitialState(state: S?) {
        updateDataState { state ?: viewState }
    }

    fun checkDataState(): S = viewState

    fun updateDataState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
        observableState.tryEmit(viewState)
    }

    fun updateDataState() {
        observableState.tryEmit(viewState)
    }

    fun updateState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
    }

    fun updateError(error: Throwable) {
        errorState.value = error
    }

    fun updateDialogState(state: VRODialogState, clearView: Boolean = true) {
        if (clearView) updateDataState { viewState }
        dialogState.value = state
    }

    open fun onStart() = Unit

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }

    open fun onResume() {
        updateState { viewState }
    }

    fun navigate(destination: D?) {
        navigationState.value = VRONavigationState(destination)
    }

    fun navigateBack(result: Serializable? = null) {
        navigationState.value = VRONavigationState(navigateBack = true, backResult = result)
    }

    open fun setOnResult(result: Serializable) = Unit
}