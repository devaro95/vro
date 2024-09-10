package com.vro.navigation

import com.vro.navstarter.VRONavStarter

var navStarterList: HashMap<String, VRONavStarter> = hashMapOf()
var navResultList: HashMap<String, VROBackResult> = hashMapOf()

fun putStarterParam(route: String, state: VRONavStarter) {
    navStarterList[route] = state
}

fun putResultParam(route: String, state: VROBackResult) {
    navResultList[route] = state
}

fun getStarterParam(route: String): VRONavStarter? {
    val result = navStarterList[route]
    navStarterList.remove(route)
    return result
}

fun getResultParam(route: String): VROBackResult? {
    val result = navResultList[route]
    navResultList.remove(route)
    return result
}