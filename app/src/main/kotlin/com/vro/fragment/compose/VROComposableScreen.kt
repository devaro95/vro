package com.vro.fragment.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROComposableScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    lateinit var viewModel: VM

    private lateinit var navController: NavController

    @Composable
    fun CreateScreen(initialState: S?, viewModelSeed: VM, navController: NavController) {
        InitializeScreen(initialState, viewModelSeed)
        this.navController = navController
    }

    @Composable
    abstract fun ComposableContent(state: S)

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }

    fun navigate(destination: VRODestination) {
        navController.navigate(destination::class.java.name)
    }

    @Composable
    private fun InitializeScreen(initialState: S?, viewModelSeed: VM) {
        viewModel = VROScreenExtensions.createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.createInitialState()
            viewModel.onStart()
        }
        val state by viewModel.state.collectAsState(initialState ?: viewModel.initialViewState)
        ComposableContent(state)
    }
}
