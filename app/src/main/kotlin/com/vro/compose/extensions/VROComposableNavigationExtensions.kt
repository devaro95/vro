@file:JvmName("VRONavExtensionsKt")

package com.vro.compose.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import com.vro.compose.VROComposableScreen
import com.vro.navparam.VRONavParam

var navParamList: HashMap<String, VRONavParam> = hashMapOf()

fun putNavParam(route: String, state: VRONavParam) {
    navParamList[route] = state
}

fun getNavParamState(route: String): VRONavParam? {
    val result = navParamList[route]
    navParamList.remove(route)
    return result
}

@ExperimentalMaterial3Api
fun VROComposableScreen<*, *, *>.destinationRoute(): String = this::class.java.name