package com.vro.compose.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableScreenContent
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.fragment.VROViewModelFactory
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@Composable
fun <VM : VROViewModel<*, *, *>> createViewModel(viewModelSeed: VM): VM {
    return viewModel(
        viewModelSeed::class.java,
        factory = VROViewModelFactory(viewModelSeed)
    )
}

fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModelSeed: @Composable () -> VM,
    navController: NavController,
    destination: VRODestination,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> VROComposableScreenContent<VM, S, D, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
) {
    composable(
        destination::class.java.name,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition }
    ) {
        content.invoke(this, it).CreateScreen(viewModelSeed.invoke(), navController = navController)
    }
}

fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModelSeed: @Composable () -> VM,
    navController: NavController,
    destination: VRODestination,
    content: VROComposableScreenContent<VM, S, D, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
) {
    composable(
        destination::class.java.name,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition }
    ) {
        content.CreateScreen(viewModelSeed.invoke(), navController = navController)
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModelSeed: VM,
    navController: NavController,
    content: VROComposableScreenContent<VM, S, D, E>,
) {
    content.CreateScreen(viewModelSeed, navController = navController)
}