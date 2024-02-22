package com.vro.compose

import androidx.navigation.NavController
import com.vro.compose.dialog.VROComposableBottomSheet
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.extensions.putNavParam
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.navigation.VRONavigator
import com.vro.navstarter.VRONavStarter
import java.io.Serializable

abstract class VROComposableNavigator<D : VRODestination>(
    private val activity: VROComposableActivity,
    private val navController: NavController,
) : VRONavigator<D> {

    override fun navigateBack(result: Serializable?) {
        navController.previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_BACK_STATE, result)
        val canNavigateBack = navController.popBackStack()
        if (!canNavigateBack) finish()
    }

    fun navigateToScreen(
        screen: VROComposableScreenContent<*, *, *>,
        state: VRONavStarter? = null,
        popScreen: VROComposableScreenContent<*, *, *>? = null,
        inclusive: Boolean = false,
    ) {
        state?.let {
            putNavParam(screen.destinationRoute(), it)
        }
        navController.navigate(screen.destinationRoute()) {
            popScreen?.let {
                popUpTo(it.destinationRoute()) { this.inclusive = inclusive }
            }
        }
    }

    fun openBottomSheet(
        bottomSheet: VROComposableBottomSheet<*, *, *>,
    ) {
        navController.navigate(bottomSheet.destinationRoute())
    }

    override fun finish() {
        activity.finish()
    }

    companion object {
        const val NAVIGATION_BACK_STATE = "NAVIGATION_BACK_STATE"
    }
}