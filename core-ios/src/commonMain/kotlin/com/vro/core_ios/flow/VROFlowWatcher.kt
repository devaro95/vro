package com.vro.core_ios.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Subscribes to this [Flow] on the main dispatcher and forwards every emission to [onEach].
 *
 * This bridges Kotlin's `Flow`/`SharedFlow`, which aren't directly observable from Swift,
 * into a plain callback that Swift can call into. The returned [VROCancellable] should be
 * cancelled when the subscription is no longer needed (e.g. when the SwiftUI view disappears).
 *
 * No business logic is added: this is a pure structural bridge between Kotlin coroutines
 * and Swift callbacks.
 */
fun <T> Flow<T>.watch(
    scope: CoroutineScope,
    onEach: (T) -> Unit,
): VROCancellable {
    val job = scope.launch(Dispatchers.Main) {
        collect { value -> onEach(value) }
    }
    return VROCancellable(job)
}
