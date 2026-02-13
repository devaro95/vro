/**
 * Package containing extension functions for Compose screen navigation and composition.
 */
package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.composition.LocalAnimatedVisibilityScope
import com.vro.compose.initializers.*
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.compose.navigator.VROTemplateNavigator
import com.vro.compose.states.*
import com.vro.compose.template.*
import com.vro.constants.INT_ZERO
import com.vro.core_android.viewmodel.VROAndroidViewModel
import com.vro.core_android.viewmodel.VROViewModelFactory
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel

/**
 * Adds a Compose screen to the navigation graph with optional transitions.
 *
 * @param VM The ViewModel type that extends [com.vro.viewmodel.VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel Factory function for the ViewModel
 * @param navigator The navigation controller
 * @param content The screen content composable
 * @param enterTransition Optional enter transition animation
 * @param exitTransition Optional exit transition animation
 *
 * @see VROComposableScreen For the screen implementation
 * @see VroComposableScreenContent For internal content handling
 */
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> NavGraphBuilder.vroComposableTemplate(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: VROTemplate<S, E, M, R>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null
) {
    val route = content.destinationRoute()
    composable(
        route = route,
        enterTransition = { enterTransition ?: fadeIn(animationSpec = tween(INT_ZERO)) },
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(INT_ZERO)) }
    ) {
        CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
            val vm = viewModel(
                modelClass = VROAndroidViewModel::class.java,
                key = viewModel.javaClass.toString(), //TODO
                factory = VROViewModelFactory(viewModel.invoke())
            ) as VROAndroidViewModel<S, D, E>

            VroComposableTemplateContent(
                viewModel = vm,
                navigator = navigator,
                content = content
            )
        }
    }
}

/**
 * Internal implementation for Compose screen content.
 * Initializes all screen components and state listeners.
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance
 * @param navigator The navigation controller
 * @param content The screen content composable
 *
 * @see InitializeLifecycleObserver For lifecycle handling
 * @see InitializeOneTimeListener For event handling
 * @see InitializeNavigatorListener For navigation handling
 * @see InitializeStepperListener For state management
 */
@Composable
internal fun <VM : VROAndroidViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> VroComposableTemplateContent(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROTemplate<S, E, M, R>
) {
    val vm = viewModel.vroViewModel
    content.templateContent.context = LocalContext.current
    content.events = vm
    content.navController = navigator.navController
    BackHandler(true) { vm.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        viewModel = vm,
        content = content,
        screenLifecycle = screenLifecycle,
        navController = navigator.navController,
    )
    InitializeOneTimeListener(
        viewModel = vm,
        content = content
    )
    InitializeNavigatorListener(
        viewModel = vm,
        navigator = navigator
    )
    InitializeStepperListener(
        viewModel = vm,
        content = content,
        screenLifecycle = screenLifecycle
    )
    InitializeEventsListener(
        viewModel = vm
    )
}