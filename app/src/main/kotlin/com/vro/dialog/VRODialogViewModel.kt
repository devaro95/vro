package com.vro.dialog

import androidx.lifecycle.ViewModel
import com.vro.coroutine.VROConcurrencyManager
import com.vro.usecase.MainUseCaseResult
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class VRODialogViewModel<S : VROState> : ViewModel() {

    abstract val initialState: S

    private lateinit var viewState: S

    private val concurrencyManager = VROConcurrencyManager()

    private val observableState: MutableSharedFlow<S> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val state: Flow<S> = observableState

    private fun createInitialState() {
        if (!this::viewState.isInitialized) {
            viewState = initialState
        }
    }

    open fun initialize() {
        createInitialState()
    }

    fun getState() = viewState

    fun updateDataState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
        observableState.tryEmit(viewState)
    }

    fun updateState(changeStateFunction: S.() -> S) {
        viewState = changeStateFunction.invoke(viewState)
    }

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}