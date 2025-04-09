package com.vro.compose

import androidx.navigation.NavController
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.core_android.navigation.VRONavigator
import com.vro.navigation.VRODestination
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter

/**
 * Abstract base class for handling navigation in a Compose-based application.
 * Provides navigation functionality for screens and bottom sheets.
 *
 * @param D The navigation destination type that extends [VRODestination]
 * @property activity The host activity that implements [VROComposableActivity]
 * @property navController The NavController for handling navigation
 */
abstract class VROComposableNavigator<D : VRODestination>(
    override val activity: VROComposableActivity,
    override val navController: NavController
) : VRONavigator<D> {

    /**
     * Navigates to a specified screen.
     *
     * @param screen The screen to navigate to
     * @param starter Optional navigation starter parameters
     * @param popScreen Optional screen to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     */
    fun navigateToScreen(
        screen: VROScreen<*, *>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = screen.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Opens a bottom sheet with ViewModel support.
     *
     * @param bottomSheet The bottom sheet content to display
     * @param starter Optional navigation starter parameters
     * @param popScreen Optional screen to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     */
    fun openBottomSheet(
        bottomSheet: VROComposableViewModelBottomSheetContent<*, *>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = bottomSheet.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Opens a simple bottom sheet.
     *
     * @param bottomSheet The bottom sheet content to display
     * @param starter Optional navigation starter parameters
     * @param popScreen Optional screen to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     */
    fun openBottomSheet(
        bottomSheet: VROComposableBottomSheetContent<*>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = bottomSheet.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Internal navigation method that handles the actual navigation logic.
     *
     * @param destinationRoute The route to navigate to
     * @param starter Optional navigation starter parameters
     * @param popScreen Optional screen to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     */
    private fun navigateToRoute(
        destinationRoute: String,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navController.navigate(destinationRoute) {
            popScreen?.destinationRoute()?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
        }
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}