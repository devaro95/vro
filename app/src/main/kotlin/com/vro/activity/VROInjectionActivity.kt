package com.vro.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vro.event.VROEvent
import com.vro.viewmodel.VROViewModel
import com.vro.fragment.VROViewModelFactory
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROInjectionActivity<S : VROState, VM : VROViewModel<S, D, E>, D : VRODestination, E : VROEvent> : AppCompatActivity() {

    abstract val viewModelSeed: VM

    val viewModel by lazy {
        ViewModelProvider(this, VROViewModelFactory(viewModelSeed))[viewModelSeed.javaClass]
    }
}