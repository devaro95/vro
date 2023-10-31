package com.vro.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.android.scope.createScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

abstract class VROInjectionFragment<VM : ViewModel> : Fragment(), KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }

    abstract val viewModelSeed: VM

    val viewModel by lazy {
        ViewModelProvider(this, VROViewModelFactory(viewModelSeed))[viewModelSeed.javaClass]
    }
}