package com.vro.navigation

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vro.fragment.VROFragment
import com.vro.state.VROState
import java.io.Serializable

abstract class VROFragmentNavigator<D : VRODestination>(private val fragment: Fragment) : VRONavigator<D> {
    private val navController: NavController = fragment.findNavController()
    private val activity: Activity = fragment.requireActivity()
    override fun navigateBack(result: Serializable?) {
        fragment.findNavController().previousBackStackEntry?.savedStateHandle?.set(VROFragment.NAVIGATION_BACK_STATE, result)
        fragment.findNavController().popBackStack().also { hasBack ->
            if (!hasBack) activity.finish()
        }
    }

    fun navigateWithAction(action: Int, state: VROState? = null) =
        navController.navigate(action, Bundle().apply {
            putSerializable(NAVIGATION_STATE, state)
        })

    fun finish() = activity.finish()

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
    }
}