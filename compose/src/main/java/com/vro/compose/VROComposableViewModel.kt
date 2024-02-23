package com.vro.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.navstarter.VRONavStarter
import com.vro.state.VRODialogState
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.state.VroNavigationSharedFlow
import com.vro.state.VroStateDelegate
import com.vro.state.VroStepperSharedFlow
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import java.io.Serializable

abstract class VROComposableViewModel<S : VROState, D : VRODestination> : ViewModel(), VROEvent {

    abstract val initialState: S

    private var screenState: S by VroStateDelegate { initialState }

    private val observableNavigation = VroNavigationSharedFlow<D>().create()

    private val observableStepper = VroStepperSharedFlow<S>().create()

    internal val stepper: SharedFlow<VROStepper<S>> = observableStepper

    internal val navigationState: SharedFlow<VRONavigationState<D>?> = observableNavigation

    internal val screenLoaded: MutableState<Boolean> = mutableStateOf(false)

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    internal fun isLoaded() {
        screenLoaded.value = false
    }

    internal fun startViewModel() {
        executeCoroutine {
            onStart()
            hideStartLoading()
        }
    }

    open suspend fun onStart() = Unit

    open fun onNavParam(navParam: VRONavStarter?) = Unit

    fun checkDataState(): S = screenState

    fun updateScreen(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
        observableStepper.tryEmit(VROStepper.VROScreenStep(screenState))
    }

    fun updateScreen() {
        observableStepper.tryEmit(VROStepper.VROScreenStep(screenState))
    }

    fun updateState(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
    }

    fun updateDialog(dialogState: VRODialogState, clearScreen: Boolean = true) {
        if (clearScreen) updateScreen { screenState }
        observableStepper.tryEmit(VROStepper.VRODialogStep(screenState, dialogState))
    }

    private fun hideStartLoading() {
        executeCoroutine {
            screenLoaded.value = true
        }
    }

    open fun onResume() {
        updateState { screenState }
    }

    open fun onPause() = Unit

    open fun onNavResult(result: Serializable) = Unit

    fun navigate(destination: D?) {
        destination?.resetNavigated()
        observableNavigation.tryEmit(VRONavigationState(destination))
    }

    fun navigateBack(result: Serializable? = null) {
        observableNavigation.tryEmit(VRONavigationState(navigateBack = true, backResult = result))
    }

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}