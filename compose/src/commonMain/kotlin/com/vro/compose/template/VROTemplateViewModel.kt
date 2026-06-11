package com.vro.compose.template

import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

abstract class VROTemplateViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModel<S, D, E>()
