package com.vro.compose.template

import com.vro.core_android.viewmodel.VROViewModelNav
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROTemplateViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelNav<S, D, E>() {
    override fun doBack(result: VROBackResult?) = Unit
}