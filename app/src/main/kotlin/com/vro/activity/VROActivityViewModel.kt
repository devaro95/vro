package com.vro.activity

import androidx.lifecycle.ViewModel
import com.vro.VROSingleLiveEvent
import com.vro.dialog.VRODialogState
import com.vro.net.MainUseCaseResult
import com.vro.net.VROConcurrencyManager
import kotlinx.coroutines.CoroutineScope

abstract class VROActivityViewModel : ViewModel() {

    internal val dialogState: VROSingleLiveEvent<VRODialogState> = VROSingleLiveEvent()

    private val concurrencyManager = VROConcurrencyManager()

    fun updateDialogState(dialogId: VRODialogState) {
        dialogState.value = dialogId
    }

    abstract fun onStart()

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}