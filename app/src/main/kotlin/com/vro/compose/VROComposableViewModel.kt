package com.vro.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROConcurrencyManager
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.navparam.VRONavParam
import com.vro.state.VROState
import com.vro.state.VROStateHandler
import com.vro.state.VroNavigationSharedFlow
import com.vro.state.VroStateDelegate
import com.vro.state.VroStateSharedFlow
import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope
import java.io.Serializable

abstract class VROComposableViewModel<S : VROState, D : VRODestination> : ViewModel(), VROEvent {

    abstract val initialState: S

    private var screenState: S by VroStateDelegate { initialState }

    private val observableState = VroStateSharedFlow<S>().create()

    private val observableNavigation = VroNavigationSharedFlow<D>().create()

    internal val stateHandler = VROStateHandler(
        screenState = observableState,
        navigationState = observableNavigation
    )

    internal val screenLoaded: MutableState<Boolean> = mutableStateOf(false)

    internal var concurrencyManager: VROBaseConcurrencyManager = VROConcurrencyManager()

    internal fun isLoaded() {
        screenLoaded.value = false
    }

    internal fun startViewModel(result: Serializable?) {
        executeCoroutine {
            onStart()
            result?.let { onNavResult(result) }
            hideStartLoading()
        }
    }

    open suspend fun onStart() = Unit

    open fun onNavParam(navParam: VRONavParam?) = Unit

    fun checkDataState(): S = screenState

    fun updateScreen(changeStateFunction: S.() -> S) {
        stateHandler.dialogState.value = null
        screenState = changeStateFunction.invoke(screenState)
        observableState.tryEmit(screenState)
    }

    fun updateScreen() {
        stateHandler.dialogState.value = null
        observableState.tryEmit(screenState)
    }

    fun updateState(changeStateFunction: S.() -> S) {
        screenState = changeStateFunction.invoke(screenState)
    }

    fun updateDialog(dialogState: VRODialogState, clearScreen: Boolean = true) {
        if (clearScreen) updateScreen { screenState }
        stateHandler.dialogState.value = dialogState
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