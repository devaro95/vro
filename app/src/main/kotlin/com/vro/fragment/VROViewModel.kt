package com.vro.fragment

import com.vro.core_android.viewmodel.VROViewModelBasics
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelBasics<S, D, E>() {

    internal fun setInitialState(state: S?) {
        updateScreen { state ?: screenState }
    }
}