package com.vro.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.vro.compose.extensions.createViewModel
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.extensions.putNavParam
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.navparam.VRONavParam
import com.vro.state.VROState

abstract class VROComposableScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    lateinit var viewModel: VM

    private lateinit var navController: NavController

    private var initialState: S? = null

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    @Composable
    fun CreateScreen(viewModelSeed: VM, navController: NavController) {
        this@VROComposableScreen.navController = remember { navController }
        InitializeScreen(viewModelSeed)
    }

    @Composable
    abstract fun ComposableContent(state: S)

    @Composable
    open fun OnLoadDialog(data: VRODialogState) = Unit

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }

    fun navigate(destination: VRODestination, state: VRONavParam? = null) {
        state?.let {
            putNavParam(destination::class.java.name, it)
        }
        navController.navigate(destination::class.java.name)
    }

    @Composable
    private fun InitializeScreen(viewModelSeed: VM) {
        viewModel = createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.createInitialState()
            viewModel.onNavParam(getNavParamState(navController.currentDestination?.route.toString()))
            viewModel.onStart()
        }
        val state by viewModel.state.collectAsState(remember { initialState ?: viewModel.initialViewState })
        val dialogState by viewModel.dialogState.observeAsState()
        ComposableContent(state)
        dialogState?.let { OnLoadDialog(it) }
    }
}
