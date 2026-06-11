package com.vro.compose.extensions

import kotlin.reflect.KClass

fun KClass<out Any>.destinationRoute(): String = this.simpleName ?: this.toString()
