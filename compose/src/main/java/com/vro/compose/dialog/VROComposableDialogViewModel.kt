package com.vro.compose.dialog

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.*
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

abstract class VROComposableDialogViewModel<S : VROState, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialState: S

    private var screenState: S by VroStateDelegate { initialState }

    private val observableStepper = createStepperSharedFlow<S>()

    internal val stepper: SharedFlow<VROStepper<S>> = observableStepper

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    private val observableState: MutableSharedFlow<S> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state: Flow<S> = observableState

    internal fun setInitialState(initialState: S? = null) {
        initialState?.let { screenState = it }
    }

    internal fun startViewModel() {
        executeCoroutine {
            onStart()
        }
    }

    open suspend fun onStart() = Unit

    fun checkDataState(): S = screenState

    fun updateScreen(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
        observableStepper.tryEmit(VROStepper.VROStateStep(screenState))
    }

    fun updateScreen() {
        observableStepper.tryEmit(VROStepper.VROStateStep(screenState))
    }

    fun updateState(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
    }

    open fun onResume() {
        updateState { screenState }
    }

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }

    override fun eventBack(result: VROBackResult?) = Unit
}