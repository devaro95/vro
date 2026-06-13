package com.vro.core_ios.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

/**
 * iOS counterpart of [VROAndroidViewModel].
 *
 * Wraps a [VROViewModel] instance to be consumed from Swift/Objective-C via KMM.
 * No logic added — pure structural wrapper to mirror the Android pattern.
 *
 * @param S The UI state type extending [VROState].
 * @param D The navigation destination type extending [VRODestination].
 * @param E The event type extending [VROEvent].
 */
open class VROIosViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
)
