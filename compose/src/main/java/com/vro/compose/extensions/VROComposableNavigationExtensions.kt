@file:JvmName("VRONavExtensionsKt")

package com.vro.compose.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import com.vro.compose.VROComposableScreen
import com.vro.navstarter.VRONavStarter

var navParamList: HashMap<String, VRONavStarter> = hashMapOf()

fun putNavParam(route: String, state: VRONavStarter) {
    navParamList[route] = state
}

fun getNavParamState(route: String): VRONavStarter? {
    val result = navParamList[route]
    navParamList.remove(route)
    return result
}

@ExperimentalMaterial3Api
fun VROComposableScreen<*, *, *>.destinationRoute(): String = this::class.java.name