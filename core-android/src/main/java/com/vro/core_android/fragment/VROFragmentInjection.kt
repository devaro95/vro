package com.vro.core_android.fragment

import androidx.fragment.app.Fragment
import com.vro.viewmodel.VROViewModel
import com.vro.core_android.viewmodel.initializeViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class VROFragmentInjection<S : VROState, D : VRODestination, E : VROEvent, VM : VROViewModel<S, D, E>> : Fragment(), KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }

    abstract val viewModelSeed: VM

    val vm by lazy {
        initializeViewModel(this, viewModelSeed)
    }
}