package com.vro.viewmodel

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.*
import com.vro.navstarter.VRONavStarter
import com.vro.state.*
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(), VROEventListener<E> {

    abstract val initialState: S

    private var state: S by VroStateDelegate { initialState }

    private val observableStepper = VroStepperSharedFlow<S>().create()

    private val observableNavigation = VroNavigationSharedFlow<D>().create()

    private val observableError = VroMutableSharedFlow<Throwable>().create()

    val stepper: SharedFlow<VROStepper<S>> = observableStepper

    internal val errorState: SharedFlow<Throwable> = observableError

    internal val navigationState: SharedFlow<VRONavigationState<D>?> = observableNavigation

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    internal fun setInitialState(state: S?) {
        updateState { state ?: this@VROViewModel.state }
    }

    override fun eventBack(result: VROBackResult?) {
        navigateBack(result)
    }

    internal fun startViewModel() {
        onStart()
    }

    open fun onStart() = Unit

    fun checkDataState(): S = state

    fun updateState(changeStateFunction: S.() -> S) {
        state = changeStateFunction.invoke(state)
        observableStepper.tryEmit(VROStepper.VROStateStep(state))
    }

    fun updateState() {
        observableStepper.tryEmit(VROStepper.VROStateStep(state))
    }

    fun updateStateWithoutRefresh(changeStateFunction: S.() -> S) {
        state = changeStateFunction.invoke(state)
    }

    fun updateDialog(dialogState: VRODialogState, clear: Boolean = true) {
        if (clear) updateState { state }
        observableStepper.tryEmit(VROStepper.VRODialogStep(state, dialogState))
    }

    fun updateError(error: Throwable) {
        observableError.tryEmit(error)
    }

    open fun onResume() {
        updateStateWithoutRefresh { state }
    }

    open fun onPause() = Unit

    open fun onNavResult(result: VROBackResult) = Unit

    fun navigate(destination: D?) {
        destination?.resetNavigated()
        observableNavigation.tryEmit(VRONavigationState(destination))
    }

    fun navigateBack(result: VROBackResult? = null) {
        observableNavigation.tryEmit(VRONavigationState(navigateBack = true, backResult = result))
    }

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}