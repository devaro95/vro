package com.vro.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.extensions.putNavParam
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.navigation.VRONavigator
import com.vro.navparam.VRONavParam
import java.io.Serializable

@ExperimentalMaterial3Api
abstract class VROComposableNavigator<D : VRODestination>(
    private val activity: VROComposableActivity,
    private val navController: NavController,
) : VRONavigator<D> {

    override fun navigateBack(result: Serializable?) {
        navController.previousBackStackEntry?.savedStateHandle?.set(VROFragmentNavigator.NAVIGATION_BACK_STATE, result)
        val canNavigateBack = navController.popBackStack()
        if (!canNavigateBack) finish()
    }

    fun navigateToScreen(screen: VROComposableScreen<*, *, *>, state: VRONavParam? = null) {
        state?.let {
            putNavParam(screen.destinationRoute(), it)
        }
        navController.navigate(screen.destinationRoute())
    }

    override fun finish() {
        activity.finish()
    }
}