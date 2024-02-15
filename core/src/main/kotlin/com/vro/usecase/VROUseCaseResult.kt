package com.vro.usecase

import com.vro.coroutine.VROBaseConcurrencyManager
import com.vro.coroutine.VROResult
import com.vro.coroutine.VROSyncDelegate
import com.vro.coroutine.onFailure
import com.vro.coroutine.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicBoolean

class MainUseCaseResult<T>(
    concurrencyManager: VROBaseConcurrencyManager,
    fullException: Boolean,
    onAction: suspend CoroutineScope.() -> T,
) : OnSuccessFinish<T>, OnErrorFinish {

    private var result: VROResult<T, Throwable>? by VROSyncDelegate(null)

    private var successListener: ((T) -> Unit)? = null
    private var errorListener: ((Throwable) -> Unit)? = null

    private var finishListener: (() -> Unit)? = null

    private var finished: AtomicBoolean = AtomicBoolean(false)

    override val job: Job = concurrencyManager.launch(fullException = fullException) {
        try {
            val data = onAction.invoke(this)
            result = VROResult.success(data)

        } catch (e: Exception) {
            result = VROResult.failure(e)

        } finally {
            result?.apply {
                onSuccess {
                    successListener?.invoke(it)
                }.onFailure {
                    errorListener?.invoke(it)
                }
            }
            finished.set(true)
            finishListener?.invoke()
            successListener = null
            errorListener = null
            finishListener = null
        }
    }

    override fun onFinish(finishAction: () -> Unit): Job {
        if (finished.get())
            finishAction.invoke()
        else {
            finishListener = finishAction
        }
        return job
    }

    override fun onSuccess(successAction: (T) -> Unit): OnErrorFinish {
        result?.onSuccess {
            successAction.invoke(it)
        } ?: also {
            successListener = successAction
        }
        return this
    }

    override fun onError(errorAction: (Throwable) -> Unit): OnSuccessFinish<T> {
        result?.onFailure {
            errorAction.invoke(it)
        } ?: also {
            errorListener = errorAction
        }
        return this
    }
}

interface OnJob {
    val job: Job
}

interface OnFinish : OnJob {
    fun onFinish(finishAction: () -> Unit): Job
}

interface OnErrorFinish : OnFinish, OnJob {
    fun onError(errorAction: (Throwable) -> Unit): OnFinish
}

interface OnSuccessFinish<T> : OnFinish, OnJob {
    fun onSuccess(successAction: (T) -> Unit): OnFinish
}