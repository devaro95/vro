package com.vro.compose.extensions

import androidx.compose.runtime.Composable
import com.vro.compose.initializers.*
import com.vro.compose.navigator.VROIosComposableNavigator
import com.vro.compose.screen.VROScreen
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel
import kotlin.reflect.KClass

/**
 * iOS equivalent of vroComposableScreen.
 * Wires up a VROScreen with its ViewModel and navigator on iOS.
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROIosScreen(
    viewModel: VM,
    navigator: VROIosComposableNavigator<D>,
    content: KClass<out VROScreen<S, E>>,
    starter: com.vro.navstarter.VRONavStarter? = null,
) {
    val contentInstance = content.java.getDeclaredConstructor().newInstance()
    InitializeIosLifecycle(viewModel = viewModel, starter = starter)
    InitializeIosNavigatorListener(viewModel = viewModel, navigator = navigator)
    InitializeIosOneTimeListener(viewModel = viewModel, content = contentInstance)
    InitializeIosStepperListener(viewModel = viewModel, content = contentInstance)
}
