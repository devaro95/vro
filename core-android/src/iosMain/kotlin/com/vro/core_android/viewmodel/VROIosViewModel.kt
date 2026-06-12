package com.vro.core_android.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

/**
 * iOS equivalent of VROAndroidViewModel.
 * Does not extend androidx ViewModel — uses plain Kotlin class.
 */
open class VROIosViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
)
