package com.vro.core_android.viewmodel

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.*
import com.vro.navstarter.VRONavStarter
import com.vro.state.*
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROErrorStep
import com.vro.state.VROStepper.VROStateStep
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

abstract class VROViewModelBasics<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialState: S

    protected var screenState: S by VroStateDelegate { initialState }

    protected val observableStepper = createStepperSharedFlow<S>()

    private val observableNavigation = createNavigationSharedFlow<D>()

    private val observableOneTime = createOneTimeSharedFlow<S>()

    val stepper: Flow<VROStepper<S>> = observableStepper

    fun getNavigationState(): SharedFlow<VRONavigationState<D>?> = observableNavigation

    fun getOneTimeEvents(): Flow<VROOneTimeState<S>> = observableOneTime

    var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    open fun onStart() = Unit

    open fun onNavResult(result: VROBackResult) = Unit

    fun updateScreen(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
        observableStepper.tryEmit(VROStateStep(screenState))
    }

    fun updateScreen() {
        observableStepper.tryEmit(VROStateStep(screenState))
    }

    fun updateState(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
    }

    fun updateDialog(dialogState: VRODialogData, clearScreen: Boolean = true) {
        if (clearScreen) updateScreen { screenState }
        observableStepper.tryEmit(VRODialogStep(screenState, dialogState))
    }

    fun updateError(error: Throwable, data: Any? = null) {
        observableStepper.tryEmit(VROErrorStep(screenState, error, data))
    }

    fun updateOneTime(id: Int, state: S) {
        observableOneTime.tryEmit(VROOneTimeState.Launch(state = state, id = id))
    }

    fun clearOneTime() {
        observableOneTime.tryEmit(VROOneTimeState.Clear())
    }

    fun navigate(destination: D?) {
        destination?.resetNavigated()
        observableNavigation.tryEmit(VRONavigationState(destination))
    }

    override fun eventBack(result: VROBackResult?) {
        navigateBack(result)
    }

    fun navigateBack(result: VROBackResult? = null) {
        observableNavigation.tryEmit(VRONavigationState(navigateBack = true, backResult = result))
    }

    open fun onPause() = Unit

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