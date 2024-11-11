package com.vro.core_android.navigation

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.vro.navigation.*

interface VRONavigator<D : VRODestination> {

    val navController: NavController

    val activity: ComponentActivity

    fun navigate(destination: D)

    fun navigateBack(result: VROBackResult?) {
        result?.let {
            putResultParam(navController.previousBackStackEntry?.destination?.id.toString(), result)
        }
        navController.popBackStack().also { hasBack ->
            if (!hasBack) finish()
        }
    }

    fun finish() = activity.finish()

    fun startActivity(intent: Intent) = activity.startActivity(intent)

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
    }
}