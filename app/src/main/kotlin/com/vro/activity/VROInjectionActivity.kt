package com.vro.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vro.core_android.viewmodel.VROViewModelFactory
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROActivityInjection<S : VROState, VM : VROViewModel<S, D, E>, D : VRODestination, E : VROEvent> : AppCompatActivity() {

    abstract val viewModelSeed: VM

    val viewModel by lazy {
        ViewModelProvider(this, VROViewModelFactory(viewModelSeed))[viewModelSeed.javaClass]
    }
}