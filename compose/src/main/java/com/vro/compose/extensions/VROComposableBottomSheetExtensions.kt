package com.vro.compose.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.dialog.VROComposableBottomSheet
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@OptIn(ExperimentalMaterialNavigationApi::class)
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroBottomSheet(
    viewModel: @Composable () -> VM,
    content: VROComposableBottomSheet<S, D, E>,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
) {
    bottomSheet(
        route = content.destinationRoute(),
    ) {
        content.CreateBottomSheet(
            viewModel = viewModel.invoke(),
            navController = navController,
            navigator = navigator
        )
    }
}