package com.vro.core_ios.viewmodel

import com.vro.core_ios.flow.VROCancellable
import com.vro.core_ios.flow.watch
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.state.VROOneTimeState
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.viewmodel.VROViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * iOS counterpart of [VROAndroidViewModel].
 *
 * Wraps a [VROViewModel] instance to be consumed from Swift/Objective-C via KMM.
 * No business logic added — pure structural wrapper to mirror the Android pattern.
 *
 * In addition to exposing [vroViewModel] directly, this class provides Swift-friendly
 * `watchXxx` methods that bridge Kotlin `Flow`/`SharedFlow` properties into plain
 * callbacks, since `Flow` is not directly observable from Swift.
 *
 * @param S The UI state type extending [VROState].
 * @param D The navigation destination type extending [VRODestination].
 * @param E The event type extending [VROEvent].
 */
open class VROIosViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>,
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /** Observes screen state changes (state, skeleton, dialog and error steps). */
    fun watchStepper(onEach: (VROStepper<S>) -> Unit): VROCancellable =
        vroViewModel.stepper.watch(scope, onEach)

    /** Observes navigation requests (destination changes and back results). */
    fun watchNavigation(onEach: (VRONavigationState<D>?) -> Unit): VROCancellable =
        vroViewModel.getNavigationState().watch(scope, onEach)

    /** Observes one-time UI events (e.g. snackbars, toasts) tied to the screen state. */
    fun watchOneTimeEvents(onEach: (VROOneTimeState<S>) -> Unit): VROCancellable =
        vroViewModel.getOneTimeEvents().watch(scope, onEach)

    /** Observes events launched via the [com.vro.event.VROEventLauncher]. */
    fun watchEvents(onEach: (E) -> Unit): VROCancellable =
        vroViewModel.eventObservable.watch(scope, onEach)

    /** Cancels every subscription created through the `watchXxx` methods. */
    fun clear() {
        scope.coroutineContext[kotlinx.coroutines.Job]?.cancel()
    }
}
