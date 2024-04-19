@file:JvmName("VRONavExtensionsKt")

package com.vro.compose.extensions

import com.vro.compose.VROScreen
import com.vro.compose.dialog.VROComposableBottomSheetContent
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

fun VROScreen<*, *, *>.destinationRoute(): String = this::class.java.name

fun VROComposableBottomSheetContent<*, *, *>.destinationRoute(): String = this::class.java.name