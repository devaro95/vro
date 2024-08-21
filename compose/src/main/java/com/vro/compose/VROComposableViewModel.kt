package com.vro.compose

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

abstract class VROComposableViewModel<S : VROState, D : VRODestination, E : VROEvent> : ViewModel(),
    VROEventListener<E> {

    abstract val initialState: S

    private var screenState: S by VroStateDelegate { initialState }

    private val observableNavigation = createNavigationSharedFlow<D>()

    private val observableStepper = createStepperSharedFlow<S>()

    internal val stepper: Flow<VROStepper<S>> = observableStepper

    internal val navigationState: SharedFlow<VRONavigationState<D>?> = observableNavigation

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    open fun onBackSystem() {
        navigateBack(null)
    }

    override fun eventBack(result: VROBackResult?) {
        navigateBack(result)
    }

    internal fun startViewModel() {
        onStart()
    }

    open fun onStart() = Unit

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    open fun onCreate(navParam: VRONavStarter?) {
        observableStepper.tryEmit(VROStepper.VROStateStep(initialState))
        onNavParam(navParam)
    }

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

    fun updateDialog(dialogState: VRODialogState, clearScreen: Boolean = true) {
        if (clearScreen) updateScreen { screenState }
        observableStepper.tryEmit(VROStepper.VRODialogStep(screenState, dialogState))
    }

    fun updateError(error: Throwable, data: Any? = null) {
        observableStepper.tryEmit(VROStepper.VROErrorStep(error, data))
    }

    open fun onResume() {
        updateState { screenState }
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

    fun showSkeleton() {
        observableStepper.tryEmit(VROStepper.VROSkeletonStep(screenState))
    }

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}