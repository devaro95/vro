package com.vro.navigation

import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vro.state.VROState

abstract class VROActivityNavigator<D : VRODestination>(private val activity: Activity, navHostId: Int) : VRONavigator<D> {

    private val navController: NavController = activity.findNavController(navHostId)

    override fun navigateBack(result: VROBackResult?) {
        navController.previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_BACK_STATE, result)
        navController.popBackStack().also { hasBack ->
            if (!hasBack) activity.finish()
        }
    }

    fun navigateWithAction(action: Int, state: VROState? = null) =
        navController.navigate(action, Bundle().apply {
            putSerializable(NAVIGATION_STATE, state)
        })

    override fun finish() = activity.finish()

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
        const val NAVIGATION_BACK_STATE = "NAVIGATION_BACK_STATE"
    }
}