package com.vro.core_android.navigation

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vro.navigation.VRODestination
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter

abstract class VROFragmentNavigator<D : VRODestination>(fragment: Fragment) : VRONavigator<D> {

    override val activity: ComponentActivity by lazy { fragment.requireActivity() }

    override val navController: NavController by lazy { fragment.findNavController() }

    fun navigateWithAction(action: Int, starter: VRONavStarter? = null) {
        navController.navigate(action)
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}