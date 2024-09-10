package com.vro.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vro.core_android.navigation.VRONavigator
import com.vro.core_android.navigation.VRONavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState

abstract class VROActivityNavigator<D : VRODestination>(
    navHostId: Int,
    final override val activity: ComponentActivity,
    override val navController: NavController = activity.findNavController(navHostId),
) : VRONavigator<D> {

    fun navigateWithAction(action: Int, state: VROState? = null) =
        navController.navigate(action, Bundle().apply {
            putSerializable(NAVIGATION_STATE, state)
        })
}