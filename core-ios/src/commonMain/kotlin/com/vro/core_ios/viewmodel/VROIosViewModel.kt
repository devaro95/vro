package com.vro.core_ios.viewmodel

import com.vro.core_ios.flow.VROCancellable
import com.vro.core_ios.flow.watch
import com.vro.core_ios.flow.watchNonNull
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.state.VROOneTimeState
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.viewmodel.VROViewModel

/**
 * iOS counterpart of [VROAndroidViewModel].
 *
 * Wraps a [VROViewModel] instance to be consumed from Swift via KMM.
 *
 * In addition to exposing the raw [vroViewModel], this class provides
 * Swift-friendly `watch*` functions that bridge Kotlin [kotlinx.coroutines.flow.Flow]s
 * to plain callbacks, since `Flow`/`SharedFlow` are not directly observable
 * from Swift.
 *
 * @param S The UI state type extending [VROState].
 * @param D The navigation destination type extending [VRODestination].
 * @param E The event type extending [VROEvent].
 */
open class VROIosViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
) {

    /**
     * Observes screen state updates (state changes, skeleton, dialog, error steps).
     */
    fun watchStepper(onEach: (VROStepper<S>) -> Unit): VROCancellable =
        vroViewModel.stepper.watch(onEach)

    /**
     * Observes navigation events triggered by [com.vro.viewmodel.VROViewModelNav.navigate].
     * `null` navigation states (no pending navigation) are filtered out.
     */
    fun watchNavigation(onEach: (VRONavigationState<D>) -> Unit): VROCancellable =
        vroViewModel.getNavigationState().watchNonNull(onEach)

    /**
     * Observes one-time UI events (e.g. snackbars, toasts) emitted via
     * [com.vro.viewmodel.VROViewModelCore.updateOneTime].
     */
    fun watchOneTimeEvents(onEach: (VROOneTimeState<S>) -> Unit): VROCancellable =
        vroViewModel.getOneTimeEvents().watch(onEach)

    /**
     * Observes events emitted via [com.vro.event.VROEventLauncher].
     */
    fun watchEvents(onEach: (E) -> Unit): VROCancellable =
        vroViewModel.eventObservable.watch(onEach)

    /** Forwards a UI event to the wrapped [vroViewModel]. */
    fun onEvent(event: E) = vroViewModel.onEvent(event)

    /** Forwards the system back action to the wrapped [vroViewModel]. */
    fun onBackSystem() = vroViewModel.onBackSystem()

    /** Triggers [com.vro.viewmodel.VROViewModelCore.onStart]. */
    fun start() = vroViewModel.onStart()

    /** Returns the current screen state synchronously. */
    fun currentState(): S = vroViewModel.checkDataState()
}
