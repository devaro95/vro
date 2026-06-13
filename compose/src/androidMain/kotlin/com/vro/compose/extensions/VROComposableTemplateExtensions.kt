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
import com.vro.compose.template.*
import com.vro.constants.INT_ZERO
import com.vro.core_android.viewmodel.VROAndroidViewModel
import com.vro.core_android.viewmodel.VROViewModelFactory
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VROViewModel
import kotlin.reflect.KClass

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
