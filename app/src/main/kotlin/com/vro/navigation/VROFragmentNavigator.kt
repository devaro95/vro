package com.vro.navigation

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vro.state.VROState

abstract class VROFragmentNavigator<D : VRODestination>(fragment: Fragment) : VRONavigator<D> {

    private val navController: NavController = fragment.findNavController()

    val activity: Activity = fragment.requireActivity()

    override fun navigateBack(result: VROBackResult?) {
        navController.previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_BACK_STATE, result)
        navController.popBackStack().also { hasBack ->
            if (!hasBack) activity.finish()
        }
    }

    fun navigateWithAction(action: Int, state: VROState? = null) {
        navController.navigate(action, Bundle().apply {
            putSerializable(NAVIGATION_STATE, state)
        })
    }

    override fun finish() = activity.finish()

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
        const val NAVIGATION_BACK_STATE = "NAVIGATION_BACK_STATE"
    }
}