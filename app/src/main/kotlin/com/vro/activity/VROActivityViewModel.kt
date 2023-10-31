package com.vro.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vro.model.VRODialogData
import com.vro.net.VROConcurrencyManager
import com.vro.net.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope

abstract class VROActivityViewModel : ViewModel() {

    private val dialogLiveData: MutableLiveData<VRODialogData> = MutableLiveData()
    internal val dialog: LiveData<VRODialogData>
        get() = dialogLiveData

    private val concurrencyManager = VROConcurrencyManager()

    fun updateDialogState(dialogId: VRODialogData) {
        dialogLiveData.value = dialogId
    }

    abstract fun onStart()

    fun <T> executeCoroutine(
        fullException: Boolean = false,
        action: suspend CoroutineScope.() -> T,
    ): MainUseCaseResult<T> {
        return MainUseCaseResult(concurrencyManager, fullException, action)
    }
}