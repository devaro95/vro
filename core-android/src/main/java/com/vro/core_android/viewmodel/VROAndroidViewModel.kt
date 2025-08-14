package com.vro.core_android.viewmodel

import androidx.lifecycle.ViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

open class VROAndroidViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
) : ViewModel()

