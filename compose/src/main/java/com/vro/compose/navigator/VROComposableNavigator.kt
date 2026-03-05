package com.vro.compose.navigator

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.compose.template.VROTemplate
import com.vro.core_android.navigation.VRONavigator
import com.vro.navigation.VRODestination
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter
import kotlin.reflect.KClass

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
    override val navController: NavController,
) : VRONavigator<D> {

    /**
     * Navigates to a specified screen.
     *
     * @param screen The screen to navigate to
     * @param starter Optional navigation starter parameters
     * @param popDestination Optional screen to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     * @param singleTop Whether to avoid multiple copies of the same destination
     */
    fun navigate(
        screen: KClass<out Any>,
        starter: VRONavStarter? = null,
        popDestination: KClass<out Any>? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {
        navigateToRoute(
            destinationRoute = screen.destinationRoute(),
            starter = starter,
            popDestination = popDestination,
            inclusive = inclusive,
            singleTop = singleTop
        )
    }

    /**
     * Internal navigation method that handles the actual navigation logic.
     *
     * @param destinationRoute The route to navigate to
     * @param starter Optional navigation starter parameters
     * @param popDestination Optional template to pop from back stack
     * @param inclusive Whether to include the popScreen in the pop operation
     * @param singleTop Whether to avoid multiple copies of the same destination
     */
    private fun navigateToRoute(
        destinationRoute: String,
        starter: VRONavStarter? = null,
        popDestination: KClass<out Any>? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {

        activity.hideKeyboard()
        navController.navigate(destinationRoute) {
            popDestination?.destinationRoute()?.let { route ->
                popUpTo(route) {
                    this.inclusive = inclusive
                }
            }
            launchSingleTop = singleTop
        }
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}