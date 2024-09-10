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
        navController.popBackStack().also { hasBack ->
            if (!hasBack) finish()
        }
        result?.let {
            putResultParam(navController.currentDestination?.id.toString(), result)
        }
    }

    fun finish() = activity.finish()

    fun startActivity(intent: Intent) = activity.startActivity(intent)

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
    }
}