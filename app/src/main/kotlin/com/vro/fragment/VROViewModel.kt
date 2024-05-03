package com.vro.fragment

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.*
import com.vro.navigation.*
import com.vro.navstarter.VRONavStarter
import com.vro.state.*
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialViewState: S

    private var viewState: S by VroStateDelegate { initialViewState }

    private val observableStepper = VroStepperSharedFlow<S>().create()

    val stepper: SharedFlow<VROStepper<S>> = observableStepper

    internal val errorState: VROSingleLiveEvent<Throwable> = VROSingleLiveEvent()

    internal val navigationState: VROSingleLiveEvent<VRONavigationState<D>> = VROSingleLiveEvent()

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    internal fun setInitialState(state: S?) {
        updateDataState { state ?: viewState }
    }

    fun checkDataState(): S = viewState

    fun updateDataState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
        observableStepper.tryEmit(VROStepper.VROStateStep(viewState))
    }

    fun updateDataState() {
        observableStepper.tryEmit(VROStepper.VROStateStep(viewState))
    }

    fun updateState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
    }

    fun updateError(error: Throwable) {
        errorState.value = error
    }

    fun updateDialogState(dialogState: VRODialogState, clearView: Boolean = true) {
        if (clearView) updateDataState { viewState }
        observableStepper.tryEmit(VROStepper.VRODialogStep(viewState, dialogState))
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

    fun navigateBack(result: VROBackResult? = null) {
        navigationState.value = VRONavigationState(navigateBack = true, backResult = result)
    }

    open fun setOnResult(result: VROBackResult) = Unit
}