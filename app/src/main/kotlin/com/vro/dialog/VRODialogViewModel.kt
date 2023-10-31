package com.vro.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vro.net.VROConcurrencyManager
import com.vro.net.MainUseCaseResult
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope

abstract class VRODialogViewModel<S : VROState> : ViewModel() {

    abstract val initialState: S

    private lateinit var viewState: S

    private val concurrencyManager = VROConcurrencyManager()

    private val _state: MutableLiveData<S> = MutableLiveData()
    val state: LiveData<S>
        get() = _state

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
        _state.value = viewState
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