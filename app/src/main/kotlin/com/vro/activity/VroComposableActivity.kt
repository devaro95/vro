package com.vro.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VroComposableActivity<
        S : VROState,
        VM : VROViewModel<S, D, E>,
        D : VRODestination,
        E : VROEvent,
        > : VROInjectionActivity<S, VM, D, E>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.createInitialState()
        setContent {
            createContent()
        }
        onCreated()
    }

    @Composable
    abstract fun createContent()

    @CallSuper
    abstract fun onCreated()

}