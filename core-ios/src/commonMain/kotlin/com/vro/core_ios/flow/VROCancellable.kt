package com.vro.core_ios.flow

import kotlinx.coroutines.Job

/**
 * Lightweight cancellation handle returned by [watch] functions.
 *
 * Allows Swift code to stop observing a Kotlin [kotlinx.coroutines.flow.Flow]
 * without exposing coroutine internals (Job) directly to Swift.
 */
class VROCancellable(private val job: Job) {

    /**
     * Cancels the underlying collection coroutine.
     * Call this from `deinit` / `onDisappear` on the Swift side to avoid leaks.
     */
    fun cancel() {
        job.cancel()
    }

    val isActive: Boolean
        get() = job.isActive
}
