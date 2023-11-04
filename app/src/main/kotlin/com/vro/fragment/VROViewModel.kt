package com.vro.fragment

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.vro.VROSingleLiveEvent
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.net.MainUseCaseResult
import com.vro.net.VROBaseConcurrencyManager
import com.vro.net.VROConcurrencyManager
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.Serializable

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel() {

    abstract val initialViewState: S

    private lateinit var viewState: S

    private val internalState: MutableStateFlow<S> by lazy { MutableStateFlow(initialViewState) }

    val state: MutableStateFlow<S>
        get() = internalState

    internal val dialogState: VROSingleLiveEvent<VRODialogState> = VROSingleLiveEvent()

    internal val errorState: VROSingleLiveEvent<Throwable> = VROSingleLiveEvent()

    internal val navigationState: VROSingleLiveEvent<VRONavigationState<D>> = VROSingleLiveEvent()

    private fun createInitialState() {
        if (!this::viewState.isInitialized) {
            viewState = initialViewState
        }
    }

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    internal fun onInitializeState() {
        createInitialState()
    }

    internal fun setInitialState(state: S?) {
        updateDataState { state ?: viewState }
        onStart()
    }

    fun checkDataState(): S = viewState

    fun updateDataState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
        state.update { viewState }
    }

    fun updateDataState() {
        state.update { viewState }
    }

    fun updateState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
    }

    fun updateError(error: Throwable) {
        errorState.value = error
    }

    fun updateDialogState(dialogId: VRODialogState, clearView: Boolean = true) {
        if (clearView) updateDataState { viewState }
        dialogState.value = dialogId
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

    fun unBindObservables(lifecycleOwner: LifecycleOwner) {
        navigationState.removeObservers(lifecycleOwner)
        dialogState.removeObservers(lifecycleOwner)
        errorState.removeObservers(lifecycleOwner)
    }
}