package com.vro.viewmodel

import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navstarter.VRONavStarter
import com.vro.state.VRODialogData
import com.vro.state.VROOneTimeState
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.state.VroStateDelegate
import com.vro.state.createEventSharedFlow
import com.vro.state.createOneTimeSharedFlow
import com.vro.state.createStepperSharedFlow
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class VROViewModelCore<S : VROState, E : VROEvent> : VROEventLauncher<E> {

    val id: String
        get() {
            return "ViewModelID: ${this::class.simpleName}"
        }

    abstract val initialState: S

    protected var screenState: S by VroStateDelegate { initialState }

    protected val observableStepper = createStepperSharedFlow<S>()

    private val observableOneTime = createOneTimeSharedFlow<S>()

    val stepper: Flow<VROStepper<S>> = observableStepper

    fun getOneTimeEvents(): Flow<VROOneTimeState<S>> = observableOneTime

    var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    override val eventObservable: MutableSharedFlow<E> = createEventSharedFlow()

    open fun onStarter(starter: VRONavStarter?) = Unit

    open fun onStart() = Unit

    open fun getResult() = Unit

    abstract fun onEvent(event: E)

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

    fun updateDialog(dialogState: VRODialogData, clearScreen: Boolean = true) {
        if (clearScreen) updateScreen { screenState }
        observableStepper.tryEmit(VROStepper.VRODialogStep(screenState, dialogState))
    }

    fun updateError(error: Throwable, data: Any? = null) {
        observableStepper.tryEmit(VROStepper.VROErrorStep(screenState, error, data))
    }

    fun updateOneTime(id: Int, state: S) {
        observableOneTime.tryEmit(VROOneTimeState.Launch(state = state, id = id))
    }

    fun clearOneTime() {
        observableOneTime.tryEmit(VROOneTimeState.Clear())
    }

    open fun onPause() = Unit

    open fun onDestroy() = Unit

    open fun onResume() {
        updateState { screenState }
    }

    fun checkDataState(): S = screenState

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}