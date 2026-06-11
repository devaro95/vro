package com.vro.compose.navigator

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.extensions.destinationRoute
import com.vro.core_android.navigation.VRONavigator
import com.vro.navigation.VRODestination
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter
import kotlin.reflect.KClass

abstract class VROComposableNavigator<D : VRODestination>(
    override val activity: VROComposableActivity,
    override val navController: NavController,
) : VRONavigator<D> {

    fun navigate(
        destination: KClass<out Any>,
        starter: VRONavStarter? = null,
        popDestination: KClass<out Any>? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {
        navigateToRoute(
            destinationRoute = destination.destinationRoute(),
            starter = starter,
            popDestination = popDestination,
            inclusive = inclusive,
            singleTop = singleTop
        )
    }

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
                popUpTo(route) { this.inclusive = inclusive }
            }
            launchSingleTop = singleTop
        }
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}
