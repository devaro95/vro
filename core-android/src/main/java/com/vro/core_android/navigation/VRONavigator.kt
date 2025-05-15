package com.vro.core_android.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.vro.constants.INT_ZERO
import com.vro.navigation.*

interface VRONavigator<D : VRODestination> {

    val navController: NavController

    val activity: ComponentActivity

    fun navigate(destination: D)

    fun navigateBack(result: VROBackResult?) {
        result?.let {
            putResultParam(result.id, result)
        }
        navController.popBackStack().also { hasBack ->
            if (!hasBack) finish()
        }
    }

    fun finish() = activity.finish()

    fun startActivity(intent: Intent) = activity.startActivity(intent)

    @SuppressLint("NewApi")
    fun Activity.hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, INT_ZERO)
    }

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
    }
}