package com.vro.core_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

open class VROAndroidViewModel<S : VROState, D : VRODestination, E : VROEvent>(
    val vroViewModel: VROViewModel<S, D, E>
) : ViewModel()

