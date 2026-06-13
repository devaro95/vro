package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.composition.LocalAnimatedVisibilityScope
import com.vro.compose.initializers.*
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.compose.screen.*
import com.vro.compose.template.*
import com.vro.constants.INT_ZERO
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.core_android.viewmodel.*
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel
import kotlin.reflect.KClass

fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: KClass<out VROScreen<S, E>>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null
) {
    val route = content.destinationRoute()
    composable(
        route = route,
        enterTransition = { enterTransition ?: fadeIn(animationSpec = tween(400)) },
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(400)) }
    ) {
        CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
            val vm = viewModel(
                modelClass = VROAndroidViewModel::class.java,
                key = viewModel.javaClass.toString(),
                factory = VROViewModelFactory(viewModel.invoke())
            ) as VROAndroidViewModel<S, D, E>
            VroComposableScreenContent(viewModel = vm, navigator = navigator, content = content)
        }
    }
}

@Composable
fun <VM : VROAndroidViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: KClass<out VROScreen<S, E>>
) {
    VroComposableScreenContent(viewModel = viewModel, navigator = navigator, content = content)
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableFragmentScreen(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: KClass<out VROScreen<S, E>>
) {
    val vm = viewModel(
        modelClass = VROAndroidViewModel::class.java,
        key = viewModel.javaClass.toString(),
        factory = VROViewModelFactory(viewModel)
    ) as VROAndroidViewModel<S, D, E>
    VroComposableScreenContent(viewModel = vm, navigator = navigator, content = content)
}

@Composable
internal fun <VM : VROAndroidViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: KClass<out VROScreen<S, E>>
) {
    val vm = viewModel.vroViewModel
    val contentInstance = content.java.getDeclaredConstructor().newInstance()
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current
    contentInstance.screenContent.setAndroidContext(context)
    contentInstance.navController = navigator.navController
    BackHandler(true) { vm.onBackSystem() }
    InitializeLifecycleObserver(viewModel = vm, content = contentInstance, screenLifecycle = screenLifecycle, navController = navigator.navController)
    InitializeOneTimeListener(viewModel = vm, content = contentInstance)
    InitializeNavigatorListener(viewModel = vm, navigator = navigator)
    InitializeStepperListener(viewModel = vm, content = contentInstance, screenLifecycle = screenLifecycle)
    InitializeEventsListener(viewModel = vm)
}

@Composable
internal fun <VM : VROAndroidViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: KClass<out VROScreen<S, E>>
) {
    val vm = viewModel.vroViewModel
    val contentInstance = content.java.getDeclaredConstructor().newInstance()
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current
    contentInstance.screenContent.setAndroidContext(context)
    BackHandler(true) { vm.onBackSystem() }
    InitializeLifecycleObserver(viewModel = vm, screenLifecycle = screenLifecycle, navController = navigator.navController, content = contentInstance)
    InitializeOneTimeListener(viewModel = vm, content = contentInstance)
    InitializeNavigatorListener(viewModel = vm, navigator = navigator)
    InitializeStepperListener(viewModel = vm, content = contentInstance, screenLifecycle = screenLifecycle)
    InitializeEventsListener(viewModel = vm)
}

fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> NavGraphBuilder.vroComposableTemplate(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: KClass<out VROTemplate<S, E, M, R>>,
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
                key = viewModel.javaClass.toString(),
                factory = VROViewModelFactory(viewModel.invoke())
            ) as VROAndroidViewModel<S, D, E>
            VroComposableTemplateContent(viewModel = vm, navigator = navigator, content = content)
        }
    }
}

@Composable
internal fun <VM : VROAndroidViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> VroComposableTemplateContent(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: KClass<out VROTemplate<S, E, M, R>>
) {
    val vm = viewModel.vroViewModel
    val contentInstance = content.java.getDeclaredConstructor().newInstance()
    val context = LocalContext.current
    contentInstance.templateContent.context = context
    contentInstance.navController = navigator.navController
    BackHandler(true) { vm.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(viewModel = vm, content = contentInstance, screenLifecycle = screenLifecycle, navController = navigator.navController)
    InitializeOneTimeListener(viewModel = vm, content = contentInstance)
    InitializeNavigatorListener(viewModel = vm, navigator = navigator)
    InitializeStepperListener(viewModel = vm, content = contentInstance, screenLifecycle = screenLifecycle)
    InitializeEventsListener(viewModel = vm)
}
