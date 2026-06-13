package com.vro.core_ios.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

/**
 * iOS counterpart of [VROTemplateViewModel] wrapper.
 *
 * Use this when building white-label (template-based) screens targeting iOS via KMM.
 * Wraps the shared [VROViewModel] for consumption from Swift.
 *
 * @param S The UI state type extending [VROState].
 * @param D The navigation destination type extending [VRODestination].
 * @param E The event type extending [VROEvent].
 */
open class VROIosTemplateViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
)
