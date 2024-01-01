package com.vro.compose.extensions

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