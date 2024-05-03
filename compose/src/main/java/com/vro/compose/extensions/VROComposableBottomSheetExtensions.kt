package com.vro.compose.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VroComposableBottomSheetContent
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@OptIn(ExperimentalMaterialNavigationApi::class)
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroBottomSheet(
    viewModel: @Composable () -> VM,
    content: VROComposableBottomSheetContent<S, D, E>,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    showSkeleton: Boolean = false
) {
    bottomSheet(
        route = content.destinationRoute(),
    ) {
        VroComposableBottomSheetContent(
            viewModel = viewModel.invoke(),
            navController = navController,
            navigator = navigator,
            content = content,
            showSkeleton = showSkeleton
        )
    }
}