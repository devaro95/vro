package com.vro.core_ios.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Shared scope used to observe VRO flows from Swift.
 *
 * Uses [Dispatchers.Main] so callbacks land on the main thread, which is
 * required to safely update SwiftUI / UIKit state.
 */
val vroIosScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

/**
 * Collects this [Flow] on [vroIosScope], invoking [onEach] for every emission.
 *
 * Intended to be called from Swift, where collecting a [Flow] directly is not
 * possible. Returns a [VROCancellable] that MUST be cancelled when the
 * observing view disappears, to avoid leaking the underlying coroutine.
 *
 * Example (Swift):
 * ```swift
 * let cancellable = viewModel.watchStepper { stepper in
 *     // update UI
 * }
 * // later
 * cancellable.cancel()
 * ```
 */
fun <T> Flow<T>.watch(onEach: (T) -> Unit): VROCancellable {
    val job = vroIosScope.launch {
        collect { onEach(it) }
    }
    return VROCancellable(job)
}

/**
 * Same as [watch] but skips `null` emissions, useful for
 * [SharedFlow]s such as [com.vro.navigation.VRONavigationState] where
 * `null` represents "no navigation pending".
 */
fun <T> SharedFlow<T?>.watchNonNull(onEach: (T) -> Unit): VROCancellable {
    val job = vroIosScope.launch {
        filterNotNull().collect { onEach(it) }
    }
    return VROCancellable(job)
}
