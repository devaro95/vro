package com.vro.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.event.VROSingleLiveEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.navparam.VRONavParam
import com.vro.net.VROBaseConcurrencyManager
import com.vro.net.VROConcurrencyManager
import com.vro.state.VROState
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.io.Serializable

abstract class VROComposableViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialViewState: S

    private lateinit var viewState: S

    private val observableState: MutableSharedFlow<S> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val observableNavigation: MutableSharedFlow<VRONavigationState<D>?> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state: Flow<S> = observableState

    internal val dialogState: VROSingleLiveEvent<VRODialogState> = VROSingleLiveEvent()

    internal val startLoading: MutableState<Boolean> = mutableStateOf(false)

    internal val errorState: VROSingleLiveEvent<Throwable> = VROSingleLiveEvent()

    internal val navigationState: SharedFlow<VRONavigationState<D>?> = observableNavigation

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    internal fun initialize() {
        startLoading.value = false
        if (!this::viewState.isInitialized) {
            viewState = initialViewState
        }
    }

    open fun onNavParam(navParam: VRONavParam?) = Unit

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

    internal fun startViewModel(result: Serializable?) {
        executeCoroutine {
            onStart()
            result?.let { setOnResult(result) }
            hideStartLoading()
        }
    }

    open suspend fun onStart() = Unit

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }

    open fun onResume() {
        updateState { viewState }
    }

    open fun onPause() = Unit

    private fun hideStartLoading() {
        executeCoroutine {
            startLoading.value = true
        }
    }

    open fun setOnResult(result: Serializable) = Unit

    fun navigate(destination: D?) {
        destination?.resetNavigated()
        observableNavigation.tryEmit(VRONavigationState(destination))
    }

    fun navigateBack(result: Serializable? = null) {
        observableNavigation.tryEmit(VRONavigationState(navigateBack = true, backResult = result))
    }
}