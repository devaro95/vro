package com.vro.core_android.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.vro.navigation.*

interface VROAndroidNavigator<D : VRODestination> : VRONavigator<D> {

    val navController: NavController
    val activity: ComponentActivity

    override fun navigateBack(result: VROBackResult?) {
        result?.let { putResultParam(result.id, result) }
        navController.popBackStack().also { hasBack ->
            if (!hasBack) finish()
        }
    }

    override fun finish() = activity.finish()

    fun startActivity(intent: Intent) = activity.startActivity(intent)

    @SuppressLint("NewApi")
    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}
