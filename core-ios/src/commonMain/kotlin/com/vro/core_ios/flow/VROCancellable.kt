package com.vro.core_ios.flow

import kotlinx.coroutines.Job

/**
 * Handle returned when subscribing to a Kotlin [kotlinx.coroutines.flow.Flow] from Swift.
 *
 * Swift cannot keep a raw Kotlin coroutine [Job] alive idiomatically, so this wraps it
 * behind a simple `cancel()` call. Call [cancel] (e.g. in `onDisappear` / `deinit`) to stop
 * observing and avoid leaks.
 */
class VROCancellable(private val job: Job) {
    fun cancel() {
        job.cancel()
    }

    val isActive: Boolean
        get() = job.isActive
}
