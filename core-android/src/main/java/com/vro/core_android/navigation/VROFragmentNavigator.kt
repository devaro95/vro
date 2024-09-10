package com.vro.core_android.navigation

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vro.navigation.VRODestination
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter

abstract class VROFragmentNavigator<D : VRODestination>(
    fragment: Fragment,
    final override val activity: ComponentActivity = fragment.requireActivity(),
    override val navController: NavController = fragment.findNavController(),
) : VRONavigator<D> {

    fun navigateWithAction(action: Int, starter: VRONavStarter? = null) {
        navController.navigate(action)
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}