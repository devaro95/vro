package com.vro.activity

import androidx.appcompat.app.AppCompatActivity
import com.vro.viewmodel.VROViewModel
import com.vro.core_android.viewmodel.initializeViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROActivityInjection<S : VROState, VM : VROViewModel<S, D, E>, D : VRODestination, E : VROEvent> : AppCompatActivity() {

    abstract val viewModelSeed: VM

    val vm by lazy {
        initializeViewModel(this, viewModelSeed)
    }
}